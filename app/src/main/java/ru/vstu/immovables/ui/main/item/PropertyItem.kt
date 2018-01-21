package ru.vstu.immovables.ui.main.item

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item
import ru.vstu.immovables.Parcels
import ru.vstu.immovables.readNullableValue
import ru.vstu.immovables.readStringList
import ru.vstu.immovables.repository.location.LocationData

sealed class PropertyItem : Item, Parcelable {

    override fun describeContents(): Int = 0

    abstract fun hasValue(): Boolean

    class Select(
            override val id: Long,
            val title: String,
            val items: List<String>,
            var selectedItem: Int = -1
    ) : PropertyItem() {

        override fun hasValue(): Boolean = selectedItem > -1

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            writeLong(id)
            writeString(title)
            writeStringList(items)
            writeInt(selectedItem)
        }

        override fun describeContents(): Int = 0

        companion object {

            @JvmField
            val CREATOR = Parcels.creator {
                Select(
                        id = readLong(),
                        title = readString(),
                        items = readStringList(),
                        selectedItem = readInt()
                )
            }

        }

    }

    class Location(
            override val id: Long,
            val title: String,
            var locationData: LocationData? = null
    ) : PropertyItem() {

        override fun hasValue(): Boolean = locationData != null

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            writeLong(id)
            writeString(title)
            writeValue(locationData)
        }

        override fun describeContents(): Int = 0

        companion object {

            @JvmField
            val CREATOR = Parcels.creator {
                Location(
                        id = readLong(),
                        title = readString(),
                        locationData = readNullableValue()
                )
            }

        }

    }

    class NumberInput(
            override val id: Long,
            val title: String,
            var value: String = ""
    ) : PropertyItem() {

        override fun hasValue(): Boolean = value.isNotEmpty()

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            writeLong(id)
            writeString(title)
            writeString(value)
        }

        override fun describeContents(): Int = 0

        companion object {

            @JvmField
            val CREATOR = Parcels.creator {
                NumberInput(
                        id = readLong(),
                        title = readString(),
                        value = readString()
                )
            }

        }

    }

}