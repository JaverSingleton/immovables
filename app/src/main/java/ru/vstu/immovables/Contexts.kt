package ru.vstu.immovables

import android.content.Context
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat

fun Context.getDrawable(@DrawableRes drawableRes: Int) =
        ContextCompat.getDrawable(this, drawableRes)

fun Context.getDimen(@DimenRes dimenRes: Int) =
        resources.getDimensionPixelSize(dimenRes)