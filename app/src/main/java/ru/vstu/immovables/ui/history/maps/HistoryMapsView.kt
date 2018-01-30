package ru.vstu.immovables.ui.history.maps

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.vstu.immovables.R
import ru.vstu.immovables.getDimen


interface HistoryMapsView {

    fun showMarker(location: LatLng, name: String, cost: String): Observable<Unit>

    fun clearMarkers(): Completable

    fun zoomTo(points: List<LatLng>): Completable

}

class HistoryMapsViewImpl(
        mapFragment: SupportMapFragment
) : HistoryMapsView {

    private val context = mapFragment.context

    private val mapRelay = BehaviorRelay.create<GoogleMap>()
    private val markerClicks = PublishRelay.create<Marker>()

    init {
        mapFragment.getMapAsync {
            mapRelay.accept(it)
            it.setOnInfoWindowClickListener { markerClicks.accept(it) }
        }
    }

    override fun showMarker(location: LatLng, name: String, cost: String): Observable<Unit> = getMap()
            .map { map ->
                map.addMarker(
                        MarkerOptions()
                                .position(location)
                                .title(name)
                                .snippet(context.getString(R.string.Common_Cost, cost))
                )
            }
            .toObservable()
            .flatMap { marker -> markerClicks.filter { it == marker } }
            .map { Unit }

    override fun clearMarkers(): Completable = changeMap { map ->
        map.clear()
    }

    override fun zoomTo(points: List<LatLng>): Completable = changeMap { map ->
        val bounds = points.fold(LatLngBounds.Builder()) { builder, point ->
            builder.include(point)
        }.build()
//        map.animateCamera(CameraUpdateFactory.newLatLngBounds(
//                bounds,
//                context.getDimen(R.dimen.maps_padding)
//        ))
    }

    private fun getMap(): Single<GoogleMap> = mapRelay.firstElement().toSingle()

    private fun changeMap(func: (map: GoogleMap) -> Unit): Completable =
            mapRelay.doOnNext { func(it) }.firstElement().ignoreElement()

}