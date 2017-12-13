package ru.vstu.immovables.ui.main.holders

import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.views.DropDownListView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DropDownListViewHolder(view: View) : BaseViewHolder(view), DropDownListView {

    private val title: TextView = view.findViewById(R.id.title)
    private val choose: TextView = view.findViewById(R.id.choose)
    private val chooseGroup: FrameLayout = view.findViewById(R.id.choose_group)


    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setDescription(description: String) {

    }

    override fun setSelected(choosen: String) {
        choose.text = choosen
    }

    override fun setClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener.invoke() }
    }
}