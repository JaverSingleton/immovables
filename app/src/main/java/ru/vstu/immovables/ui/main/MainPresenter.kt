package ru.vstu.immovables.ui.main

import android.net.Uri
import android.os.Bundle
import ru.vstu.immovables.repository.location.LocationData

interface MainPresenter : PropertiesInfoProvider {
    fun onCreate(savedState: Bundle?)
    fun onDestroy()
    fun onSaveState(): Bundle
    fun onItemSelected(id: Long, selectedValue: Int)
    fun onLocationSelected(id: Long, selectedValue: LocationData)
    fun onPhotoSelected(id: Long, selectedValue: List<Uri>)
}