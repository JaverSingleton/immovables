package ru.vstu.immovables.ui.main.views

interface Clickable {
    fun setClickListener(listener: () -> Unit)
}