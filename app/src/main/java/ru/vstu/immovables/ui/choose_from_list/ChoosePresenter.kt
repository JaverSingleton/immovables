package ru.vstu.immovables.ui.choose_from_list

interface ChoosePresenter {
    fun onCreate(elementId: Long?, data: Array<String>?)
    fun onClick(position: Int)
    fun onDestroy()
}