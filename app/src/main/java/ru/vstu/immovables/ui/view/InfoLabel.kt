package ru.vstu.immovables.ui.view

import android.view.View
import android.widget.TextView
import ru.vstu.immovables.R
import ru.vstu.immovables.setVisible

class InfoLabel(private val view: View) {
    private val indicator: View = view.findViewById(R.id.info_label_indicator)
    private val textView: TextView = view.findViewById(R.id.info_label_text)

    fun setValues(text: CharSequence?, infoLevel: InfoLevel = InfoLevel.INFO) {
        setVisible(!text.isNullOrEmpty())
        textView.text = text
        indicator.setBackgroundResource(colorResByInfoLevel(infoLevel))
    }

    fun setVisible(visible: Boolean) {
        view.setVisible(visible)
    }

    private fun colorResByInfoLevel(infoLevel: InfoLevel): Int {
        return when (infoLevel) {
            InfoLevel.INFO -> R.color.info_label_color_normal
            InfoLevel.WARNING -> R.color.info_label_color_warning
            InfoLevel.ERROR -> R.color.info_label_color_error
        }
    }

    fun clear() {
        textView.text = ""
        indicator.setBackgroundResource(R.color.info_label_color_hidden)
        view.alpha = 0f
    }
}