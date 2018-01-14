package ru.vstu.immovables.ui.location

import android.location.Geocoder
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.view.View
import android.widget.EditText
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChangeEvents
import com.jakewharton.rxbinding2.widget.textChanges
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.vstu.immovables.R
import ru.vstu.immovables.fromListener
import ru.vstu.immovables.hideKeyboard
import ru.vstu.immovables.setVisible
import java.util.concurrent.TimeUnit

interface LocationView {

    fun setApplyVisible(isShown: Boolean)

    fun showMarker(location: LatLng, name: String): Completable

    fun showQuery(query: String)

    fun updateSearchResult()

    fun queryChanged(): Observable<String>

    fun applyClicked(): Observable<Unit>

    fun cancelClicked(): Observable<Unit>

    fun mapClicked(): Observable<LatLng>

    fun hideKeyboard()

}

class LocationViewImpl(
        view: View,
        mapFragment: SupportMapFragment,
        adapterPresenter: AdapterPresenter,
        viewHolderFactory: ViewHolderBuilder<BaseViewHolder>
) : LocationView {

    private val context = view.context

    private val mapRelay = BehaviorRelay.create<GoogleMap>()

    private val searchInput: EditText = view.findViewById(R.id.search_query)

    private val applyButton: View = view.findViewById(R.id.apply_button)

    private val closeButton: View = view.findViewById(R.id.close_button)

    private val adapter = SimpleRecyclerAdapter(adapterPresenter, viewHolderFactory)

    private val recycler: RecyclerView = view.findViewById(R.id.suggests_recycler)

    init {
        mapFragment.getMapAsync { mapRelay.accept(it) }
        searchInput.hint = buildTextWithIcon(context.getString(R.string.Location_InputAddress))

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
    }

    private fun buildTextWithIcon(hint: String): CharSequence? {
        val spannable = SpannableString(SPAN_SYMBOL + hint)
        val imageSpan = buildSearchIconSpan()
        spannable.setSpan(imageSpan, LINE_START, DRAWABLE_POSITION, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        return spannable
    }

    private fun buildSearchIconSpan(): ImageSpan {
        val searchIcon = context.getDrawable(R.drawable.ic_search_16_with_padding)
        searchIcon?.setBounds(0, 0, searchIcon.intrinsicWidth, searchIcon.intrinsicHeight)
        return ImageSpan(searchIcon, DynamicDrawableSpan.ALIGN_BASELINE)
    }

    override fun setApplyVisible(isShown: Boolean) {
        applyButton.setVisible(isShown)
    }

    override fun showQuery(query: String) {
        searchInput.tag = ""
        searchInput.setText(query)
        searchInput.tag = null
    }

    override fun updateSearchResult() {
        adapter.notifyDataSetChanged()
    }

    override fun queryChanged(): Observable<String> = searchInput
            .textChangeEvents()
            .skip(1)
            .filter { it.view().tag == null }
            .distinctUntilChanged()
            .debounce(1, TimeUnit.SECONDS)
            .map { it.text().toString() }

    override fun applyClicked(): Observable<Unit> = applyButton.clicks()

    override fun cancelClicked(): Observable<Unit> = closeButton.clicks()

    override fun mapClicked(): Observable<LatLng> = getMap()
            .flatMapObservable { map -> fromListener<LatLng> { map.setOnMapClickListener(it) } }

    override fun showMarker(location: LatLng, name: String): Completable = changeMap { map ->
        map.clear()
        map.addMarker(MarkerOptions().position(location).title(name))
        map.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun hideKeyboard() {
        searchInput.hideKeyboard()
    }

    private fun getMap(): Single<GoogleMap> = mapRelay.firstElement().toSingle()

    private fun changeMap(func: (map: GoogleMap) -> Unit): Completable =
            mapRelay.doOnNext { func(it) }.firstElement().ignoreElement()

}

private const val LINE_START = 0
private const val DRAWABLE_POSITION = 1
private const val SPAN_SYMBOL = " "