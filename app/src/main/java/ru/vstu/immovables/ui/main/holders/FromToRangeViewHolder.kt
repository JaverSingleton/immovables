package ru.vstu.immovables.ui.main.holders

import android.view.View
import android.widget.TextView
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.views.FromToRangeView

/**
 * Created by kkruchinin on 26.11.17.
 */
class FromToRangeViewHolder(view: View): BaseViewHolder(view), FromToRangeView {

    private val title: TextView = view.findViewById(R.id.title)
    private val fromInput: TextView = view.findViewById(R.id.fromInput)
    private val toInput: TextView = view.findViewById(R.id.toInput)

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setDescription(description: String) {

    }

    override fun setFromInput(input: String) {
        fromInput.text = input
    }

    override fun setToInput(input: String) {
        toInput.text = input
    }
}