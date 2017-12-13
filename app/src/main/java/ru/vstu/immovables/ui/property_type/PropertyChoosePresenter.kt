package ru.vstu.immovables.ui.property_type

/**
 * Created by Mekamello on 13.12.17.
 */
interface PropertyChoosePresenter {
    fun onCreate()
    fun onResume()
    fun onDestroy()
    fun onClick(position: Int)
}