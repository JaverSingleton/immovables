package ru.vstu.immovables.ui.main.item.photo

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.view.InfoLabel
import ru.vstu.immovables.ui.view.InfoLevel
import ru.vstu.immovables.ui.view.SelectableSingleLine
import ru.vstu.immovables.ui.view.SelectableSingleLineImpl


class PhotoItemView(view: View) : BaseViewHolder(view) {
    private val context = view.context

    private val selectableView: SelectableSingleLine = SelectableSingleLineImpl(view.findViewById(R.id.select))
    private val infoView: InfoLabel = InfoLabel(view.findViewById(R.id.info))

    fun setInfo(info: String?, level: InfoLevel = InfoLevel.INFO) {
        infoView.setValues(info, level)
    }

    fun setValue(title: String, count: Int, max: Int) {
        if (max > 0 && count <= 0) {
            val countText = context.getString(R.string.Field_Photo_Hint_Count, max)
            val hint = context.getString(R.string.Field_Photo_Hint, title, countText).trim()
            selectableView.setHint(hint.withHighlight(countText))
        } else {
            selectableView.setHint(title)
        }

        if (count == 0) {
            selectableView.setValue(null)
            return
        }

        val selectedCountText: String = if (max > 0) {
            context.getString(R.string.Field_Photo_SelectedCount_Multi, count, max)
        } else {
            context.getString(R.string.Field_Photo_SelectedCount_Single, count)
        }
        val valueText: String = context.getString(R.string.Field_Photo_Value, selectedCountText)
        selectableView.setValue(valueText.withHighlight(selectedCountText))
    }

    fun setClickListener(listener: () -> Unit) {
        selectableView.setOnClickListener { listener() }
    }

    fun setClearClickListener(listener: () -> Unit) {
        selectableView.setOnClearClickListener { listener() }
    }

    private fun String.withHighlight(text: String): CharSequence {
        val result = SpannableString(this)
        val startIndex = result.indexOf(text)
        val endIndex = startIndex + text.length
        result.setSpan(StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return result
    }

}