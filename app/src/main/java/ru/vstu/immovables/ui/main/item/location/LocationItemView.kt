package ru.vstu.immovables.ui.main.item.location

import android.view.View
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.ui.view.SelectableSingleLine
import ru.vstu.immovables.ui.view.SelectableSingleLineImpl


class LocationItemView(view: View) : BaseViewHolder(view) {

    private val selectableView: SelectableSingleLine = SelectableSingleLineImpl(view)

    fun setValue(value: String?) {
        selectableView.setValue(value)
    }

    fun setTitle(value: String) {
        selectableView.setHint(value)
    }

    fun setClickListener(listener: () -> Unit) {
        selectableView.setOnClickListener { listener() }
    }

    fun setClearClickListener(listener: () -> Unit) {
        selectableView.setOnClearClickListener { listener() }
    }

}