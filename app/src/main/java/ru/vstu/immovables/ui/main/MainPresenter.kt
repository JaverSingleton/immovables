package ru.vstu.immovables.ui.main

import android.os.Bundle
import ru.vstu.immovables.repository.location.LocationData

interface MainPresenter {
    fun onCreate(savedState: Bundle?)
    fun onDestroy()
    fun onSaveState(): Bundle
    fun onItemSelected(id: Long, selectedValue: Int)
    fun onLocationSelected(id: Long, selectedValue: LocationData)
}