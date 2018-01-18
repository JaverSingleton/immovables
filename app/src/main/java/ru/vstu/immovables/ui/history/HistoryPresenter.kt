package ru.vstu.immovables.ui.history

import com.avito.konveyor.adapter.AdapterPresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.ui.history.item.HistoryItem
import ru.vstu.immovables.updateItems
import ru.vstu.immovables.use

interface HistoryPresenter {

    fun attachView(view: HistoryView)

    fun attachRouter(router: Router)

    fun detachRouter()

    fun detachView()

    interface Router {

        fun openReport(reportId: Long)

        fun addImmovable()

    }

}

class HistoryPresenterImpl(
        private val reportRepository: ReportRepository,
        private val adapterPresenter: AdapterPresenter,
        private val itemsClicks: Observable<HistoryItem>
) : HistoryPresenter {

    private var view: HistoryView? = null
    private var router: HistoryPresenter.Router? = null

    private val disposables = CompositeDisposable()
    private val viewDisposables = CompositeDisposable()

    override fun attachView(view: HistoryView) {
        this.view = view

        viewDisposables += view.addClicks().subscribe {
            router?.addImmovable()
        }

        viewDisposables += itemsClicks.subscribe {
            use(router, it.report.id) { router, id ->
                router.openReport(id)
            }
        }

    }

    override fun attachRouter(router: HistoryPresenter.Router) {
        this.router = router
        disposables += reportRepository.getAll().subscribe { items ->
            adapterPresenter.updateItems(items.mapIndexed { index, reportData ->
                HistoryItem(index.toLong(), reportData)
            })
            view?.updateItems()
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