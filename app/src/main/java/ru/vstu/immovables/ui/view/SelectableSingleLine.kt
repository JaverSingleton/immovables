package ru.vstu.immovables.ui.view

import android.content.res.ColorStateList
import android.content.res.Resources
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import ru.vstu.immovables.*


interface SelectableSingleLine {

    fun setValue(value: String?)

    fun setClearButtonVisible(visible: Boolean)

    fun setHasError(hasError: Boolean)

    fun setHint(hint: CharSequence)

    fun setHint(@StringRes hintRes: Int)

    fun setEnabled(enabled: Boolean)

    fun show()

    fun hide()

    fun clearClicks(): Observable<Unit>

    fun clicks(): Observable<Unit>

    fun setOnClearClickListener(listener: () -> Unit)

    fun setOnClickListener(listener: () -> Unit)

}

class SelectableSingleLineImpl(private val rootView: View) : SelectableSingleLine {

    private val resources: Resources = rootView.context.resources

    private val hintView: TextView = rootView.findViewById(R.id.hint)
    private val valueView: TextView = rootView.findViewById(R.id.value)
    private val resetButton: View = rootView.findViewById(R.id.reset_button)
    private val iconView: View = rootView.findViewById(R.id.icon)

    private val hintTextColor: ColorStateList = hintView.textColors
    private val errorTextColor: ColorStateList = ContextCompat.getColorStateList(rootView.context, R.color.text_error_disableable)!!
    private var hasValue: Boolean = false

    override fun setValue(value: String?) {
        valueView.text = value
        hasValue = !value.isNullOrEmpty()
        hintView.setVisible(hasValue)
        valueView.setCenterVertical(!hasValue)
        setClearButtonVisible(hasValue)
        updateIconViewVisibility()
    }

    override fun setClearButtonVisible(visible: Boolean) {
        resetButton.setVisible(visible)
        updateIconViewVisibility()
    }

    override fun setHasError(hasError: Boolean) {
        if (hasError) {
            hintView.setTextColor(errorTextColor)
            valueView.setHintTextColor(errorTextColor)
        } else {
            hintView.setTextColor(hintTextColor)
            valueView.setHintTextColor(hintTextColor)
        }
    }

    override fun setHint(hint: CharSequence) {
        hintView.text = hint
        valueView.hint = hint
    }

    override fun setHint(hintRes: Int) {
        hintView.setText(hintRes)
        valueView.setHint(hintRes)
    }

    override fun setEnabled(enabled: Boolean) {
        rootView.setViewHierarchyEnabled(enabled)
    }

    override fun show() {
        rootView.show()
    }

    override fun hide() {
        rootView.hide()
    }

    override fun clearClicks(): Observable<Unit> = resetButton.clicks()

    override fun clicks(): Observable<Unit> = rootView.clicks()

    override fun setOnClearClickListener(listener: () -> Unit) {
        resetButton.setOnClickListener { listener() }
    }

    override fun setOnClickListener(listener: () -> Unit) {
        rootView.setOnClickListener { listener() }
    }

    private fun updateIconViewVisibility() {
        iconView.setVisible(!(resetButton.isVisible() && hasValue))
    }

    private fun View.setCenterVertical(isCenterVertical: Boolean) {
        with(valueView.layoutParams as RelativeLayout.LayoutParams) {
            if (isCenterVertical) {
                addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
            } else {
                addRule(RelativeLayout.CENTER_VERTICAL, 0)
            }
            requestLayout()
        }
    }

}