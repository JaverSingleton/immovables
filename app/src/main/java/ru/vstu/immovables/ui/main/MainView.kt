package ru.vstu.immovables.ui.main

import ru.vstu.immovables.ui.main.items.Filter

interface MainView {
    fun showHousingParameters(propertyFilters: List<Filter>)
    fun updateHousingParameters(propertyFilters: List<Filter>)
    fun chooseForResult(elementId: Long, chooseIn: List<String>)
    fun showNotImplementedPropertyTypeMessage()
    fun hide()
}