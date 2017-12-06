package ru.vstu.immovables.ui.main.holders

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.views.DropDownListView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DropDownListViewHolder(view: View) : BaseViewHolder(view), DropDownListView {

    private val title: TextView = view.findViewById(R.id.title)
    private val description: TextView = view.findViewById(R.id.info_label_text)
    private val choose: TextView = view.findViewById(R.id.choose)

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setDescription(description: String) {
        this.description.text = description
    }

    override fun setSelected(choosen: String) {
        choose.text = choosen
    }

    override fun setClickListener(listener: () -> Unit) {
        choose.setOnClickListener { listener.invoke() }
    }
}