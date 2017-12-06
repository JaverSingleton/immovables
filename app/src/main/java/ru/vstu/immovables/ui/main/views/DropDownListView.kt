package ru.vstu.immovables.ui.main.views

/**
 * Created by kkruchinin on 26.11.17.
 */
interface DropDownListView : FilterItemView, Clickable {
    fun setSelected(choosen: String)
}