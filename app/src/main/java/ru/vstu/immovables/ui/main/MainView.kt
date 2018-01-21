package ru.vstu.immovables.ui.main

import io.reactivex.Observable
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.repository.report.ReportData
import ru.vstu.immovables.ui.main.item.PropertyItem

interface MainView {
    fun updateItems(items: List<PropertyItem>)
    fun updateItem(position: Int)
    fun setApplyButtonVisible(visible: Boolean)
    fun selectLocation(id: Long, selectedValue: LocationData?)
    fun selectItem(id: Long, title: String, items: List<String>, selectedValue: Int)
    fun showNotImplementedPropertyTypeMessage()
    fun showTitle(title: String)
    fun applyClicks(): Observable<Unit>
    fun showReport(reportData: ReportData)
    fun close()
    fun showLoading()
    fun hideLoading()
    fun showProgress(progress: Float)
}