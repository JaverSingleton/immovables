package ru.vstu.immovables

import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat


fun Drawable.setHotspotCompat(x: Float, y: Float) {
    DrawableCompat.setHotspot(this, x, y)
}