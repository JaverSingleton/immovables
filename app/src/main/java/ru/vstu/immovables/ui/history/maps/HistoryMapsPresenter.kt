package ru.vstu.immovables.ui.history.maps

import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import ru.vstu.immovables.repository.report.ReportData
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.ui.history.HistoryRouter

interface HistoryMapsPresenter {

    fun attachView(view: HistoryMapsView)

    fun attachRouter(router: HistoryRouter)

    fun detachRouter()

    fun detachView()

}

class HistoryMapsPresenterImpl(
        private val reportRepository: ReportRepository
) : HistoryMapsPresenter {

    private var view: HistoryMapsView? = null
    private var router: HistoryRouter? = null

    private val disposables = CompositeDisposable()
    private val viewDisposables = CompositeDisposable()

    override fun attachView(view: HistoryMapsView) {
        this.view = view
    }

    override fun attachRouter(router: HistoryRouter) {
        this.router = router

        disposables += reportRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { reports -> view!!.clearMarkers().toSingleDefault(reports) }
                .flatMap { reports ->
                    view!!.zoomTo(reports.map { it.latLng }).toSingleDefault(reports)
                }
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { report ->
                    view!!.showMarker(
                            location = report.latLng,
                            name = report.address,
                            cost = report.cost.toString()
                    ).map { report }
                }
                .map { it.id }
                .subscribe { reportId -> router.openReport(reportId) }
    }

    override fun detachRouter() {
        disposables.clear()
        this.router = null
    }

    override fun detachView() {
        viewDisposables.clear()
        this.view = null
    }

    private val ReportData.latLng get() = LatLng(latitude.toDouble(), longitude.toDouble())

}