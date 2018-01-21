package ru.vstu.immovables

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
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

fun View.isVisible() = visibility == View.VISIBLE

inline fun ViewGroup.forEachChild(operation: View.() -> Unit) {
    for (i in 0 until childCount) {
        getChildAt(i).operation()
    }
}

fun View.setViewHierarchyEnabled(enabled: Boolean) {
    isEnabled = enabled
    if (this is ViewGroup) {
        forEachChild {
            setViewHierarchyEnabled(enabled)
        }
    }
}

@SuppressLint("ObsoleteSdkInt")
fun View.setBackgroundCompat(drawable: Drawable) {
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
        this.setBackgroundDrawable(drawable)
    } else {
        this.background = drawable
    }
}

fun View.changePadding(
        left: Int = paddingLeft,
        top: Int = paddingTop,
        right: Int = paddingRight,
        bottom: Int = paddingBottom
) = setPadding(left, top, right, bottom)

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

fun View.showKeyboard(flag: Int = InputMethodManager.SHOW_IMPLICIT) {
    requestFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, flag)
}