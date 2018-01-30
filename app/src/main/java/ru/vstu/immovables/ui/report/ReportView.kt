package ru.vstu.immovables.ui.report

import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import ru.vstu.immovables.R

interface ReportView {

    fun showCost(cost: String)

    fun showMaxCost(cost: String)

    fun showMinCost(cost: String)

    fun showAveCost(cost: String)

    fun reportClicks(): Observable<Unit>

    fun closeClicks(): Observable<Unit>

    fun shareClicks(): Observable<Unit>

}

class ReportViewImpl(view: View): ReportView {

    private val context = view.context

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    private val costView: TextView = view.findViewById(R.id.cost)
    private val aveCostView: TextView = view.findViewById(R.id.ave)
    private val maxCostView: TextView = view.findViewById(R.id.max)
    private val minCostView: TextView = view.findViewById(R.id.min)

    private val reportButton: View = view.findViewById(R.id.report)
    private val shareButton: View = view.findViewById(R.id.share)

    override fun showCost(cost: String) {
        costView.text = context.getString(R.string.Common_Cost, cost)
    }

    override fun showMaxCost(cost: String) {
        maxCostView.text = context.getString(R.string.Common_Cost, cost)
    }

    override fun showMinCost(cost: String) {
        minCostView.text = context.getString(R.string.Common_Cost, cost)
    }

    override fun showAveCost(cost: String) {
        aveCostView.text = context.getString(R.string.Report_AveCost, cost)
    }

    override fun reportClicks(): Observable<Unit> = reportButton.clicks()

    override fun closeClicks(): Observable<Unit> = toolbar.navigationClicks()

    override fun shareClicks(): Observable<Unit> = shareButton.clicks()

}