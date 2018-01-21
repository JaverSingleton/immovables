package ru.vstu.immovables.ui.main.item.location

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.view.InfoLabel
import ru.vstu.immovables.ui.view.InfoLevel
import ru.vstu.immovables.ui.view.SelectableSingleLine
import ru.vstu.immovables.ui.view.SelectableSingleLineImpl


class LocationItemView(view: View) : BaseViewHolder(view) {

    private val selectableView: SelectableSingleLine = SelectableSingleLineImpl(view.findViewById(R.id.select))
    private val infoView: InfoLabel = InfoLabel(view.findViewById(R.id.info))

    fun setInfo(info: String?, level: InfoLevel = InfoLevel.INFO) {
        infoView.setValues(info, level)
    }

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