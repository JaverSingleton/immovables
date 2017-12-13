package ru.vstu.immovables.ui.main.views

/**
 * Created by kkruchinin on 27.11.17.
 */
interface InputView : FilterItemView, Clickable{
    fun setInput(input: String)
}