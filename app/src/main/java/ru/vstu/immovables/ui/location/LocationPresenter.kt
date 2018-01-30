package ru.vstu.immovables.ui.location

import android.os.Bundle
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.data_source.ListDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import ru.vstu.immovables.completableOnNext
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.repository.location.LocationRepository
import ru.vstu.immovables.singleMap
import ru.vstu.immovables.toObservable
import ru.vstu.immovables.ui.location.item.LocationSearchItem
import ru.vstu.immovables.updateItems

interface LocationPresenter {

    fun attachView(view: LocationView)

    fun detachView()

    fun attachRouter(router: Router)

    fun detachRouter()

    fun onSaveState(): Bundle

    interface Router {

        fun applySelecting(locationData: LocationData)

        fun cancel()

    }

}

class LocationPresenterImpl(
        private val locationRepository: LocationRepository,
        private val locationSelectedRelay: Observable<LocationData>,
        private val adapterPresenter: AdapterPresenter,
        startLocation: LocationData?,
        state: Bundle?
) : LocationPresenter {

    private var view: LocationView? = null
    private var router: LocationPresenter.Router? = null
    private val disposables = CompositeDisposable()
    private var selectedLocation: LocationData? = state?.getParcelable(KEY_LOCATION) ?: startLocation
    private var locations: List<LocationData> = state?.getParcelableArrayList(KEY_LOCATIONS) ?: emptyList()
    private var query: String? = state?.getString(KEY_QUERY)

    override fun attachView(view: LocationView) {
        this.view = view

        updateApplyState()

        query?.let { view.showQuery(it) }
        view.updateSearch()
        disposables += selectedLocation.toObservable().updateMarker()

        disposables += view.queryChanged()
                .doOnNext { query = it }
                .updateSearchResult()

        disposables += locationSelectedRelay
                .updateMarker()

        disposables += view.applyClicked()
                .subscribe {
                    selectedLocation?.let { router?.applySelecting(it) }
                }

        disposables += view.cancelClicked()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    router?.cancel()
                }

        disposables += view.mapClicked()
                .flatMapSingle {
                    locationRepository.find(it)
                            .onErrorResumeNext { Single.never() }
                }
                .updateMarker()
    }

    override fun detachView() {
        disposables.clear()
        this.view = null
    }

    override fun attachRouter(router: LocationPresenter.Router) {
        this.router = router
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun onSaveState(): Bundle = Bundle().apply {
        putParcelable(KEY_LOCATION, selectedLocation)
        putString(KEY_QUERY, query)
        putParcelableArrayList(KEY_LOCATIONS, ArrayList(locations))
    }

    private fun updateApplyState() {
        view?.setApplyVisible(selectedLocation != null)
    }

    private fun Observable<LocationData>.updateMarker() = this
            .observeOn(AndroidSchedulers.mainThread())
            .completableOnNext { view?.showMarker(it.location, it.name) ?: Completable.never() }
            .subscribe {
                view?.showQuery(it.name)
                query = it.name
                selectedLocation = it
                updateApplyState()
                locations = listOf()
                view?.updateSearch()
                view?.hideKeyboard()
            }

    private fun Observable<String>.updateSearchResult() = this
            .singleMap {
                locationRepository.find(it)
                        .onErrorReturn { listOf() }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                locations = it
                view?.updateSearch()
            }

    private fun LocationView.updateSearch() {
        adapterPresenter.updateItems(
                locations.mapIndexed { index, locationData ->
                    LocationSearchItem(index.toLong(), locationData)
                }
        )
        updateSearchResult()
    }

}

private const val KEY_QUERY = "query"
private const val KEY_LOCATIONS = "locations"
private const val KEY_LOCATION = "selectedLocation"