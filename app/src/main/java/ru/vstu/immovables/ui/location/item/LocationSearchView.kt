package ru.vstu.immovables.ui.location.item

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R

class LocationSearchView(private val view: View) : BaseViewHolder(view) {

    private val valueView: TextView = view.findViewById(R.id.value)

    fun setValue(value: String) {
        valueView.text = value
    }

    fun setClickListener(listener: () -> Unit) {
        view.setOnClickListener { listener() }
    }

}