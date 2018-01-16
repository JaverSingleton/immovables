package ru.vstu.immovables

import android.content.Context
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.widget.Toast

fun Context.getDrawable(@DrawableRes drawableRes: Int) =
        ContextCompat.getDrawable(this, drawableRes)

fun Context.getDimen(@DimenRes dimenRes: Int) =
        resources.getDimensionPixelSize(dimenRes)

fun Context.showToast(@StringRes text: Int, length : Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}