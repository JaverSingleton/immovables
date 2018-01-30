package ru.vstu.immovables.ui.history.list

import com.avito.konveyor.adapter.AdapterPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.ui.history.HistoryRouter
import ru.vstu.immovables.ui.history.item.HistoryItem
import ru.vstu.immovables.updateItems
import ru.vstu.immovables.use

interface HistoryListPresenter {

    fun attachView(view: HistoryView)

    fun attachRouter(router: HistoryRouter)

    fun detachRouter()

    fun detachView()

}

class HistoryListPresenterImpl(
        private val reportRepository: ReportRepository,
        private val adapterPresenter: AdapterPresenter,
        private val itemsClicks: Observable<HistoryItem>
) : HistoryListPresenter {

    private var view: HistoryView? = null
    private var router: HistoryRouter? = null

    private val disposables = CompositeDisposable()
    private val viewDisposables = CompositeDisposable()

    override fun attachView(view: HistoryView) {
        this.view = view

        viewDisposables += itemsClicks.subscribe {
            use(router, it.report.id) { router, id ->
                router.openReport(id)
            }
        }

    }

    override fun attachRouter(router: HistoryRouter) {
        this.router = router
        disposables += reportRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { items ->
                    val reports = items.mapIndexed { index, reportData ->
                        HistoryItem(index.toLong(), reportData)
                    }
                    adapterPresenter.updateItems(reports)
                    view?.updateItems()
                    if (reports.isEmpty()) {
                        view?.showNoElements()
                    } else {
                        view?.showItems()
                    }
                }
    }

    override fun detachRouter() {
        disposables.clear()
        this.router = null
    }

    override fun detachView() {
        viewDisposables.clear()
        this.view = null
    }

}