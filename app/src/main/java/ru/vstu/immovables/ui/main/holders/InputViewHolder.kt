package ru.vstu.immovables.ui.main.holders

import android.support.design.widget.TextInputLayout
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
    private val input: TextView = view.findViewById(R.id.input)

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setDescription(description: String) {

    }

    override fun setInput(input: String) {
        this.input.text = input
    }

    override fun setClickListener(listener: () -> Unit) {
        itemView.setOnClickListener {
            title.isSelected = true
            input.requestFocus()
        }
    }
}