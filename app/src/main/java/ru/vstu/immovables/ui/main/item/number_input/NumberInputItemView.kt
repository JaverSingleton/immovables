package ru.vstu.immovables.ui.main.item.number_input

import android.text.InputType
import android.view.View
import com.avito.konveyor.adapter.BaseViewHolder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.view.*

class NumberInputItemView(view: View) : BaseViewHolder(view) {

    private val valueView: TextInputView = view.findViewById(R.id.input)
    private val infoView: InfoLabel = InfoLabel(view.findViewById(R.id.info))

    fun setInfo(info: String?, level: InfoLevel = InfoLevel.INFO) {
        infoView.setValues(info, level)
    }

    init {
        valueView.inputType = InputType.TYPE_CLASS_NUMBER
    }

    fun setValue(value: String?) {
        valueView.tag = ""
        valueView.text = value.toString()
        valueView.tag = null
    }

    fun setTitle(value: String) {
        valueView.hint = value
    }

    fun setValueChangedListener(listener: (value: String) -> Unit) {
        valueView.textChangeListener = object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (valueView.tag == null) {
                    listener(s.toString())
                }
            }
        }
    }

}