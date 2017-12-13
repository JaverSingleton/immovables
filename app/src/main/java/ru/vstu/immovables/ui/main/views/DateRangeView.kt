package ru.vstu.immovables.ui.main.views

/**
 * Created by kkruchinin on 26.11.17.
 */
interface DateRangeView: FilterItemView {
    fun setFromDate(date: String, listener: () -> Unit)
    fun setToDate(date: String, listener: () -> Unit)

    fun openFromDatePicker()
    fun openToDatePicker()
}