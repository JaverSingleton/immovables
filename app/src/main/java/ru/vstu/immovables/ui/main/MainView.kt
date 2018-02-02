package ru.vstu.immovables.ui.main

import android.net.Uri
import io.reactivex.Observable
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.repository.report.ReportData
import ru.vstu.immovables.ui.main.item.Field

interface MainView {
    fun updateItems(items: List<Field>)
    fun updateItem(position: Int)
    fun setApplyButtonVisible(visible: Boolean)
    fun selectLocation(id: Long, selectedValue: LocationData?)
    fun selectPhotos(id: Long, selectedValue: List<Uri>, maxSelectable: Int)
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