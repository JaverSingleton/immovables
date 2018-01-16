package ru.vstu.immovables.repository.location

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import ru.vstu.immovables.Parcels
import ru.vstu.immovables.readParcelable

class LocationData(
        val name: String,
        val location: LatLng
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeString(name)
        writeParcelable(location, flags)
    }

    override fun describeContents(): Int = 0

    companion object {

        @JvmField
        val CREATOR = Parcels.creator {
            LocationData(
                    name = readString(),
                    location = readParcelable()
            )
        }

    }

}