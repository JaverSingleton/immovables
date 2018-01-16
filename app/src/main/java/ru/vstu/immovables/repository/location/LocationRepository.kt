package ru.vstu.immovables.repository.location

import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle
import io.reactivex.schedulers.Schedulers

interface LocationRepository {

    fun find(query: String): Single<List<LocationData>>

    fun find(location: LatLng): Single<LocationData>

}

class LocationRepositoryImpl(private val geocoder: Geocoder) : LocationRepository {

    override fun find(query: String): Single<List<LocationData>> =
            if (query.isNotEmpty()) {
                Single.fromCallable {
                    geocoder.getFromLocationName(query, 10)
                }.map { it.map { it.toLocationData() } }
            } else {
                listOf<LocationData>().toSingle()
            }.subscribeOn(Schedulers.io())

    override fun find(location: LatLng): Single<LocationData> =
            Single.fromCallable {
                geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        10
                ).first()
            }.map { it.toLocationData() }
                    .subscribeOn(Schedulers.io())

    fun Address.toLocationData() = LocationData(
            (0..Math.min(maxAddressLineIndex, 3)).map { getAddressLine(it) }.joinToString(", "),
            LatLng(latitude, longitude)
    )

}