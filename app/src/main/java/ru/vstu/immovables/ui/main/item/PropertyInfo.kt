package ru.vstu.immovables.ui.main.item

import android.os.Parcel
import android.os.Parcelable
import ru.vstu.immovables.Parcels
import ru.vstu.immovables.readEnum
import ru.vstu.immovables.ui.view.InfoLevel
import ru.vstu.immovables.writeEnum

class PropertyInfo(
        val text: String,
        val level: InfoLevel = InfoLevel.INFO
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeString(text)
        writeEnum(level)
    }

    override fun describeContents(): Int = 0

    companion object {

        @JvmField
        val CREATOR = Parcels.creator {
            PropertyInfo(
                    text = readString(),
                    level = readEnum(InfoLevel.values())
            )
        }

    }

}