package ru.vstu.immovables.ui.history.item

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R


class HistoryItemView(private val view: View) : BaseViewHolder(view) {

    private val context = view.context

    private val titleView: TextView = view.findViewById(R.id.title)
    private val subtitleView: TextView = view.findViewById(R.id.subtitle)

    fun setTitle(title: String) {
        titleView.text = title
    }

    fun setSubtitle(subtitle: String) {
        subtitleView.text = context.getString(R.string.Common_Cost, subtitle)
    }

    fun setClickListener(listener: () -> Unit) {
        view.setOnClickListener { listener() }
    }

}