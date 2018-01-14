package ru.vstu.immovables

import android.os.BadParcelableException
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.util.ArrayMap
import android.support.v4.util.SimpleArrayMap
import android.util.Log
import ru.vstu.immovables.ParcelUtils.PARCELABLE_FALSE
import ru.vstu.immovables.ParcelUtils.PARCELABLE_TRUE
import java.lang.reflect.Modifier
import java.util.*

private const val TAG = "Parcels"
private const val EMPTY_VALUE = -1
private val creators: SimpleArrayMap<ClassLoader, SimpleArrayMap<String, Parcelable.Creator<*>>> = SimpleArrayMap()

object Parcels {
    @JvmStatic
    fun <T : Parcelable> creator(body: Parcel.() -> T) = object : Parcelable.Creator<T> {
        override fun createFromParcel(source: Parcel) = body(source)

        @Suppress("CAST_NEVER_SUCCEEDS")
        override fun newArray(size: Int) = arrayOfNulls<Any>(size) as Array<T?>
    }

    @JvmStatic
    fun Parcel.writeBoolean(value: Boolean) = writeInt(if (value) PARCELABLE_TRUE else PARCELABLE_FALSE)

    @JvmStatic
    fun Parcel.readBoolean(): Boolean = readInt() == PARCELABLE_TRUE
}

inline fun <reified T : Parcelable> Parcel.readParcelable() = readParcelable<T>(T::class.java.classLoader)

fun <T : Parcelable> Parcel.writeParcelableList(list: List<T>?, parcelableFlags: Int = 0) {
    if (list == null) {
        writeInt(-1)
        return
    }
    val classIndexMap = ArrayMap<Class<T>, Int>()
    list.forEach {
        if (!classIndexMap.contains(it.javaClass)) {
            classIndexMap.put(it.javaClass, classIndexMap.size)
        }
    }
    val classIndexMapSize = classIndexMap.size
    writeInt(classIndexMapSize)
    classIndexMap.forEach {
        writeParcelableCreator(it.key)
        writeInt(it.value)
    }
    val listSize = list.size
    writeInt(listSize)
    list.forEach {
        val creatorIndex = classIndexMap[it.javaClass] as Int
        writeInt(creatorIndex)
        it.writeToParcel(this, parcelableFlags)
    }
}

inline fun <reified T : Parcelable> Parcel.createParcelableList() = this.createParcelableList(T::class.java)

fun <T : Parcelable> Parcel.createParcelableList(clazz: Class<T>): List<T>? {
    val indexCreatorsMapSize = readInt()
    if (indexCreatorsMapSize == -1) return null

    val creators = arrayOfNulls<Parcelable.Creator<*>>(indexCreatorsMapSize)
    for (i in 0..indexCreatorsMapSize - 1) {
        val creator = readParcelableCreator(clazz.classLoader)
        val index = readInt()
        creators[index] = creator
    }
    val listSize = readInt()
    val list = ArrayList<T>(listSize)
    for (i in 0..listSize - 1) {
        val creatorIndex = readInt()
        val creator = creators[creatorIndex] as Parcelable.Creator<*>
        @Suppress("UNCHECKED_CAST")
        list.add(creator.createFromParcel(this) as T)
    }
    return list.toList()
}

fun <T : Parcelable> Parcel.writeParcelableListOfLists(list: List<List<T>>?) {
    if (list == null) {
        writeInt(-1)
        return
    }
    writeInt(list.size)
    list.forEach { writeParcelableList(it) }
}

inline fun <reified T : Parcelable> Parcel.createParcelableListOfLists(): List<List<T>>? {
    val n = readInt()
    if (n == -1) return null
    val list = mutableListOf<List<T>>()
    for (i in 0..n - 1) {
        list.add(createParcelableList<T>().orEmpty())
    }
    return list
}

/**
 *  Writes map to parcelable. Both K and V types must be writable to parcel. For example,
 *  boxed primitives (Int, float, etc), String or Parcelable
 */
fun <K, V> Parcel.writeValueMap(map: Map<K, V>?) = ParcelUtils.writeValueMap(this, map)

/**
 *  Read new map from parcelable at current data position. Both K and V types must be readable from
 *  parcel. For example, boxed primitives (Int, float, etc), String or Parcelable
 */
inline fun <reified K : Any, reified V : Any> Parcel.createValueMap(): Map<K, V>? = ParcelUtils.createValueMap(this, K::class.java, V::class.java)

fun Parcel.writeBoolean(value: Boolean) = writeInt(if (value) PARCELABLE_TRUE else PARCELABLE_FALSE)

fun Parcel.readBoolean(): Boolean = readInt() == PARCELABLE_TRUE

fun Parcel.writeNullableValue(value: Any?) = writeValue(value)

/**
 * Read a typed nullable object from a parcel.
 * The value must be written only by [Parcel.writeValue] or [writeNullableValue]
 */
inline fun <reified T : Any> Parcel.readNullableValue(): T? = readValue(T::class.java.classLoader) as? T

fun Parcel.writeLongRange(range: LongRange) {
    writeLong(range.start)
    writeLong(range.endInclusive)
}

fun Parcel.createLongRange(): LongRange {
    val start = readLong()
    val end = readLong()
    return LongRange(start, end)
}

private fun <T : Parcelable> Parcel.writeParcelableCreator(clazz: Class<T>) {
    writeString(clazz.name)
}

private fun Parcel.readParcelableCreator(loader: ClassLoader): Parcelable.Creator<*>? {
    val name = readString() ?: return null
    var creator: Parcelable.Creator<*>? = null
    synchronized(creators) {
        var map: SimpleArrayMap<String, Parcelable.Creator<*>>? = creators.get(loader)
        if (map == null) {
            map = SimpleArrayMap<String, Parcelable.Creator<*>>()
            creators.put(loader, map)
        }
        creator = map[name]
        if (creator == null) {
            try {
                // If loader == null, explicitly emulate Class.forName(String) "caller
                // classloader" behavior.
                val parcelableClassLoader = loader ?: javaClass.getClassLoader()
                // Avoid initializing the Parcelable class until we know it implements
                // Parcelable and has the necessary CREATOR field. http://b/1171613.
                val parcelableClass = Class.forName(name, false /* initialize */,
                        parcelableClassLoader)
                if (!Parcelable::class.java.isAssignableFrom(parcelableClass)) {
                    throw BadParcelableException("Parcelable protocol requires that the " + "class implements Parcelable")
                }
                val field = parcelableClass.getField("CREATOR")
                if (field.modifiers and Modifier.STATIC == 0) {
                    throw BadParcelableException("Parcelable protocol requires "
                            + "the CREATOR object to be static on class " + name)
                }
                val creatorType = field.type
                if (!Parcelable.Creator::class.java.isAssignableFrom(creatorType)) {
                    // Fail before calling Field.get(), not after, to avoid initializing
                    // parcelableClass unnecessarily.
                    throw BadParcelableException("Parcelable protocol requires a "
                            + "Parcelable.Creator object called "
                            + "CREATOR on class " + name)
                }
                creator = field.get(null) as Parcelable.Creator<*>
            } catch (e: IllegalAccessException) {
                Log.e(TAG, "Illegal access when unmarshalling: " + name, e)
                throw BadParcelableException(
                        "IllegalAccessException when unmarshalling: " + name)
            } catch (e: ClassNotFoundException) {
                Log.e(TAG, "Class not found when unmarshalling: " + name, e)
                throw BadParcelableException(
                        "ClassNotFoundException when unmarshalling: " + name)
            } catch (e: NoSuchFieldException) {
                throw BadParcelableException("Parcelable protocol requires a "
                        + "Parcelable.Creator object called "
                        + "CREATOR on class " + name)
            }

            if (creator == null) {
                throw BadParcelableException("Parcelable protocol requires a "
                        + "non-null Parcelable.Creator object called "
                        + "CREATOR on class " + name)
            }

            map.put(name, creator as Parcelable.Creator<*>)
        }
    }

    return creator
}

fun <T : Enum<T>> Parcel.writeEnum(value: T): Unit = writeInt(value.ordinal)

fun <T : Enum<T>> Parcel.writeOptEnum(value: T?): Unit {
    if (value == null) writeInt(EMPTY_VALUE) else writeEnum(value)
}

fun <E : Enum<E>> Parcel.readOptEnum(values: Array<E>): E? {
    val result = readInt()
    if (result == EMPTY_VALUE) {
        return null
    } else {
        return values[result]
    }
}

fun <E : Enum<E>> Parcel.readEnum(values: Array<E>): E = values[readInt()]