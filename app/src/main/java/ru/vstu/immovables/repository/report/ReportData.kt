package ru.vstu.immovables.repository.report

import android.os.Parcel
import android.os.Parcelable
import ru.vstu.immovables.Parcels

class ReportData(
        val id: Long = -1,
        val address: String,
        val metres: Long,
        val cost: Long,
        val filePath: String
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeLong(id)
        writeString(address)
        writeLong(metres)
        writeLong(cost)
        writeString(filePath)
    }

    override fun describeContents(): Int = 0

    companion object {

        @JvmField
        val CREATOR = Parcels.creator {
            ReportData(
                    id = readLong(),
                    address = readString(),
                    metres = readLong(),
                    cost = readLong(),
                    filePath = readString()
            )
        }

    }

}