package ru.vstu.immovables.ui.main.item

import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item
import ru.vstu.immovables.*
import ru.vstu.immovables.repository.location.LocationData

sealed class PropertyItem(open val isMandatory: Boolean = false) : Item, Parcelable {

    override fun describeContents(): Int = 0

    abstract fun hasValue(): Boolean

    class Select(
            override val id: Long,
            val title: String,
            val items: List<String>,
            var selectedItem: Int = -1,
            override val isMandatory: Boolean = false,
            val info: PropertyInfo? = null
    ) : PropertyItem() {

        override fun hasValue(): Boolean = selectedItem > -1

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            writeLong(id)
            writeBoolean(isMandatory)
            writeString(title)
            writeValue(info)
            writeStringList(items)
            writeInt(selectedItem)
        }

        override fun describeContents(): Int = 0

        companion object {

            @JvmField
            val CREATOR = Parcels.creator {
                Select(
                        id = readLong(),
                        isMandatory = readBoolean(),
                        title = readString(),
                        info = readNullableValue(),
                        items = readStringList(),
                        selectedItem = readInt()
                )
            }

        }

    }

    class Location(
            override val id: Long,
            val title: String,
            var locationData: LocationData? = null,
            override val isMandatory: Boolean = false,
            val info: PropertyInfo? = null
    ) : PropertyItem() {

        override fun hasValue(): Boolean = locationData != null

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            writeLong(id)
            writeBoolean(isMandatory)
            writeString(title)
            writeValue(info)
            writeValue(locationData)
        }

        override fun describeContents(): Int = 0

        companion object {

            @JvmField
            val CREATOR = Parcels.creator {
                Location(
                        id = readLong(),
                        isMandatory = readBoolean(),
                        title = readString(),
                        info = readNullableValue(),
                        locationData = readNullableValue()
                )
            }

        }

    }

    class NumberInput(
            override val id: Long,
            val title: String,
            var value: String = "",
            override val isMandatory: Boolean = false,
            val info: PropertyInfo? = null
    ) : PropertyItem() {

        override fun hasValue(): Boolean = value.isNotEmpty()

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            writeLong(id)
            writeBoolean(isMandatory)
            writeString(title)
            writeValue(info)
            writeString(value)
        }

        override fun describeContents(): Int = 0

        companion object {

            @JvmField
            val CREATOR = Parcels.creator {
                NumberInput(
                        id = readLong(),
                        isMandatory = readBoolean(),
                        title = readString(),
                        info = readNullableValue(),
                        value = readString()
                )
            }

        }

    }

}