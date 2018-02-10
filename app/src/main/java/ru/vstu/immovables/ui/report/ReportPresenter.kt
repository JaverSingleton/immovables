package ru.vstu.immovables.ui.report

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.toSingle
import ru.vstu.immovables.repository.report.ReportData
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.toNumberString
import ru.vstu.immovables.use
import java.text.NumberFormat

interface ReportPresenter {

    fun attachView(view: ReportView)

    fun attachRouter(router: Router)

    fun detachRouter()

    fun detachView()

    fun onSaveState(): Bundle

    interface Router {

        fun openFile(file: String)

        fun shareFile(file: String)

        fun close()

    }

}

class ReportPresenterImpl(
        private val reportId: Long,
        private val reportRepository: ReportRepository,
        state: Bundle?
) : ReportPresenter {

    private var report: ReportData? = state?.getParcelable(KEY_REPORT)

    private val disposables = CompositeDisposable()

    private var view: ReportView? = null
    private var router: ReportPresenter.Router? = null

    override fun attachView(view: ReportView) {
        this.view = view

        val currentReport = report

        disposables += (
                currentReport?.toSingle()
                        ?: reportRepository.find(reportId).observeOn(AndroidSchedulers.mainThread())
                )
                .subscribe { savedReport: ReportData ->
                    report = savedReport
                    view.showCost(savedReport.cost.toNumberString())
                    view.showMaxCost((savedReport.cost * 1.20f).toLong().toNumberString())
                    view.showMinCost((savedReport.cost * 0.80f).toLong().toNumberString())
                    view.showAveCost((savedReport.cost / savedReport.metres).toNumberString())
                }

        disposables += view.reportClicks().subscribe {
            report?.filePath?.let { router?.openFile(it) }
        }

        disposables += view.closeClicks().subscribe {
            router?.close()
        }

        disposables += view.shareClicks().subscribe {
            use(router, report) { router, report ->
                router.shareFile(report.filePath)
            }
        }
    }

    override fun attachRouter(router: ReportPresenter.Router) {
        this.router = router
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun detachView() {
        disposables.clear()
        this.view = null
    }

    override fun onSaveState(): Bundle = Bundle().apply {
        putParcelable(KEY_REPORT, report)
    }

}

private const val KEY_REPORT = "report"