package ru.vstu.immovables.ui.choose_from_list

/**
 * Created by kkruchinin on 02.12.17.
 */
interface ChooseView {
    fun showData(data: Array<String>)
    fun setResultSuccess(elementId: Long, choosenPosition: Int)
    fun setResultCancel(elementId: Long)
}