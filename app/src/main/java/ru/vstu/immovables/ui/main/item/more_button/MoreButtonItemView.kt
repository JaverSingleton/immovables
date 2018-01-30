package ru.vstu.immovables.ui.main.item.more_button

import android.view.View
import android.widget.Button
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R


class MoreButtonItemView(view: View) : BaseViewHolder(view) {

    private val button: Button = view.findViewById(R.id.button)

    fun showOpenText() {
        button.setText(R.string.Button_Open)
    }

    fun showCloseText() {
        button.setText(R.string.Button_Close)
    }

    fun setClickListener(listener: () -> Unit) {
        button.setOnClickListener { listener() }
    }

}