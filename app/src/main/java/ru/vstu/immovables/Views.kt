package ru.vstu.immovables

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.setVisible(isShown: Boolean) =
        if (isShown) {
            show()
        } else {
            hide()
        }

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.conceal() {
    visibility = View.INVISIBLE
}

@JvmOverloads
fun View.hideKeyboard(clearFocus: Boolean = true) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(rootView.windowToken, 0)
    if (clearFocus) {
        val focusPicked = ViewUtils.pickFocus(this)
        if (!focusPicked) {
            rootView.clearFocus()
        }
    }
}