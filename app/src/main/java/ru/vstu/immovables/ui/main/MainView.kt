package ru.vstu.immovables.ui.main

import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.ui.main.item.PropertyItem

interface MainView {
    fun updateItems(items: List<PropertyItem>)
    fun updateItem(position: Int)
    fun selectLocation(id: Long, selectedValue: LocationData?)
    fun selectItem(id: Long, title: String, items: List<String>, selectedValue: Int)
    fun showNotImplementedPropertyTypeMessage()
    fun showTitle(title: String)
    fun close()
}