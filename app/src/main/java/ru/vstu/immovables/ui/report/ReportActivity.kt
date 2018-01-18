package ru.vstu.immovables.ui.report

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import ru.vstu.immovables.R
import ru.vstu.immovables.appComponent
import ru.vstu.immovables.getContainerView
import ru.vstu.immovables.showToast
import ru.vstu.immovables.ui.report.di.ReportModule
import java.io.File
import javax.inject.Inject

class ReportActivity : AppCompatActivity(), ReportPresenter.Router {

    @Inject lateinit var presenter: ReportPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent
                .plus(ReportModule(
                        intent.getLongExtra(KEY_REPORT_ID, 0),
                        savedInstanceState
                ))
                .inject(this)
        setContentView(R.layout.activity_report)
        val view: ReportView = ReportViewImpl(
                getContainerView()
        )
        presenter.attachView(view)
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

    override fun close() {
        finish()
    }

    override fun openFile(file: String) {
        try {
            val reportFile = File(file)
            startActivity(
                    Intent().apply {
                        action = android.content.Intent.ACTION_VIEW
                        setDataAndType(Uri.fromFile(reportFile), reportFile.extension)
                    }
            )
        } catch(e: Exception) {
            showToast(R.string.Report_Error_WrongFormat)
        }
    }

    companion object {

        fun Context.reportSreen(reportId: Long) =
                Intent(this, ReportActivity::class.java)
                        .putExtra(KEY_REPORT_ID, reportId)

    }

}

private const val KEY_REPORT_ID = "reportId"