package ru.vstu.immovables.ui.main

import android.os.Bundle

interface MainPresenter {
    fun onCreate(savedState: Bundle?)
    fun onDestroy()
    fun onSaveState(): Bundle
    fun onActivityResult(requestCode: Int, elementId: Long?, choosenPosition: Int?)
}