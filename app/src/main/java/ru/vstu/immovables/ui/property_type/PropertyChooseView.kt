package ru.vstu.immovables.ui.property_type

/**
 * Created by Mekamello on 13.12.17.
 */
interface PropertyChooseView {
    fun showData(data: Array<String>)
    fun restoreData()
    fun openImmovableProperties(propertyType: String)
}