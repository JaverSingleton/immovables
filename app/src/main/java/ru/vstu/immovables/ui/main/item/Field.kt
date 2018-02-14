package ru.vstu.immovables.ui.main.item

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.avito.konveyor.blueprint.Item
import ru.vstu.immovables.*
import ru.vstu.immovables.repository.location.LocationData

sealed class Field(open val isMandatory: Boolean = false) : Item, Parcelable {

    override fun describeContents(): Int = 0

    class Select(
            override val id: Long,
            val title: String,
            val items: List<String>,
            var selectedItem: Int = -1,
            override val isMandatory: Boolean = false,
            override var info: PropertyInfo? = null
    ) : Field(), Property {

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
            override var info: PropertyInfo? = null
    ) : Field(), Property {

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

    class Photo(
            override val id: Long,
            val title: String,
            var photos: List<Uri> = listOf(),
            val maxSelectable: Int = 0,
            val minSelectable: Int = 1,
            override val isMandatory: Boolean = false,
            override var info: PropertyInfo? = null
    ) : Field(), Property {

        override fun hasValue(): Boolean = photos.size >= minSelectable

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            writeLong(id)
            writeString(title)
            writeParcelableList(photos)
            writeInt(maxSelectable)
            writeInt(minSelectable)
            writeBoolean(isMandatory)
            writeValue(info)
        }

        override fun describeContents(): Int = 0

        companion object {

            @JvmField
            val CREATOR = Parcels.creator {
                Photo(
                        id = readLong(),
                        title = readString(),
                        photos = createParcelableList() ?: listOf(),
                        maxSelectable = readInt(),
                        minSelectable = readInt(),
                        isMandatory = readBoolean(),
                        info = readNullableValue()
                )
            }

        }

    }

    class NumberInput(
            override val id: Long,
            val title: String,
            var value: String = "",
            override val isMandatory: Boolean = false,
            override var info: PropertyInfo? = null,
            val isFloat: Boolean = false
    ) : Field(), Property {

        override fun hasValue(): Boolean = value.isNotEmpty()

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            writeLong(id)
            writeBoolean(isMandatory)
            writeString(title)
            writeValue(info)
            writeString(value)
            writeBoolean(isFloat)
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
                        value = readString(),
                        isFloat = readBoolean()
                )
            }

        }

    }

    class MoreButton(
            override val id: Long,
            var more: Boolean = false
    ) : Field() {

        override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
            writeLong(id)
            writeBoolean(more)
        }

        override fun describeContents(): Int = 0

        companion object {

            @JvmField
            val CREATOR = Parcels.creator {
                MoreButton(
                        id = readLong(),
                        more = readBoolean()
                )
            }

        }

    }

}