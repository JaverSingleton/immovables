package ru.vstu.immovables.repository.report

import android.os.Parcel
import android.os.Parcelable
import ru.vstu.immovables.Parcels
import ru.vstu.immovables.database.entities.Report

class ReportData(
        val id: Long = 0,
        val address: String,
        val latitude: Double,
        val longitude: Double,
        val metres: Long,
        val cost: Long,
        val filePath: String
) : Parcelable {

    constructor(report: Report) : this(
            id = report.id,
            address = report.address,
            latitude = report.latitude,
            longitude = report.longitude,
            metres = report.metres,
            cost = report.cost,
            filePath = report.filePath
    )

    fun toEntity() = Report(
            id = id,
            address = address,
            latitude = latitude,
            longitude = longitude,
            metres = metres,
            cost = cost,
            filePath = filePath
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeLong(id)
        writeString(address)
        writeDouble(latitude)
        writeDouble(longitude)
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
                    latitude = readDouble(),
                    longitude = readDouble(),
                    metres = readLong(),
                    cost = readLong(),
                    filePath = readString()
            )
        }

    }

}