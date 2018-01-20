package ru.vstu.immovables.ui.property_type

interface PropertyChooseView {
    fun showData(data: List<String>, selectedItem: Int)
    fun restoreData()
    fun applySelecting(selectedItem: Int)
}