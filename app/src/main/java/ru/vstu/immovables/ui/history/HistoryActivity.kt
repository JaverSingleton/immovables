package ru.vstu.immovables.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import ru.vstu.immovables.R
import ru.vstu.immovables.appComponent
import ru.vstu.immovables.getContainerView
import ru.vstu.immovables.ui.history.di.HistoryModule
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity.Companion.propertyChooseScreen
import ru.vstu.immovables.ui.report.ReportActivity.Companion.reportSreen
import javax.inject.Inject

class HistoryActivity : AppCompatActivity(), HistoryPresenter.Router {

    @Inject lateinit var presenter: HistoryPresenter
    @Inject lateinit var adapterPresenter: AdapterPresenter
    @Inject lateinit var itemBinder: ItemBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent
                .plus(HistoryModule())
                .inject(this)
        setContentView(R.layout.activity_history)
        presenter.attachView(HistoryViewImpl(
                getContainerView(),
                adapterPresenter,
                itemBinder
        ))
    }

    override fun onStart() {
        super.onStart()
        presenter.attachRouter(this)
    }

    override fun onStop() {
        presenter.detachRouter()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun openReport(reportId: Long) {
        startActivity(reportSreen(reportId))
    }

    override fun addImmovable() {
        startActivity(propertyChooseScreen())
    }

    companion object {

        fun Context.historyScreen() =
                Intent(this, HistoryActivity::class.java)

    }

}