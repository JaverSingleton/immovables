package ru.vstu.immovables.ui.main.holders

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.views.InputView

/**
 * Created by kkruchinin on 27.11.17.
 */
class InputViewHolder(view: View): BaseViewHolder(view), InputView{

    private val title: TextView = view.findViewById(R.id.title)
    private val description: TextView = view.findViewById(R.id.info_label_text)

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setDescription(description: String) {
        this.description.text = description
    }
}