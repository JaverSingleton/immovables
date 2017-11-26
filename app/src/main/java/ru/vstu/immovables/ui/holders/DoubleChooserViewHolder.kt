package ru.vstu.immovables.ui.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.views.DateRangeView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DoubleChooserViewHolder(view: View) : BaseViewHolder(view), DateRangeView{

    private val title: TextView = view.findViewById(R.id.title)
    private val description: TextView = view.findViewById(R.id.info_label_text)

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setDescription(description: String) {
        this.description.text = description
    }

}