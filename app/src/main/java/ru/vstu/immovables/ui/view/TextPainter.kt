package ru.vstu.immovables.ui.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.View
import ru.vstu.immovables.R
import kotlin.properties.Delegates

class TextPainter(
        val view: View
) {
    var hasFocus: Boolean by Delegates.observable(false) { _, _, _ -> recalculate() }

    var hasError: Boolean by Delegates.observable(false) { _, _, _ -> recalculate() }

    var fraction: Float  by Delegates.observable(1f) { _, _, _ -> recalculate() }

    var height: Int by Delegates.observable(0) { _, _, _ ->
        expandedY = calculateExpandedY()
        collapsedY = calculateCollapsedY()
        recalculate()
    }

    var text: CharSequence by Delegates.observable("" as CharSequence) { _, _, _ ->
        expandedTextHeight = calculateTextSize(expandedTextSize)
        collapsedTextHeight = calculateTextSize(collapsedTextSize)
        expandedY = calculateExpandedY()
        collapsedY = calculateCollapsedY()
    }

    private val context = view.context
    private val paint = Paint()

    private val expandedTextSize: Float =
            context.resources.getDimensionPixelSize(R.dimen.text_input_hint_expanded_size).toFloat()
    private val collapsedTextSize: Float =
            context.resources.getDimensionPixelSize(R.dimen.text_input_hint_collapsed_size).toFloat()
    private val collapsedTopPadding: Float =
            context.resources.getDimensionPixelSize(R.dimen.text_input_small_padding).toFloat()
    private val verticalPadding: Float =
            context.resources.getDimensionPixelSize(R.dimen.text_input_big_padding).toFloat()

    private val focusedColor: Int = ContextCompat.getColor(context, R.color.blue)
    private val errorColor: Int = ContextCompat.getColor(context, R.color.red)
    private val unFocusedColor = ContextCompat.getColor(context, R.color.grey_400)

    private var currentTextSize: Float = expandedTextSize
    private var currentColor: Int = unFocusedColor
    private var currentX: Float = verticalPadding
    private var currentY: Float = 0f

    private var expandedTextHeight: Float = 0f
    private var collapsedTextHeight: Float = 0f

    private var expandedY: Float = 0f
    private var collapsedY: Float = 0f

    init {
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.LEFT
    }

    private fun calculateCollapsedY(): Float =
            if (height != 0) {
                collapsedTopPadding + collapsedTextHeight
            } else {
                0f
            }

    private fun calculateExpandedY(): Float =
            if (height != 0) {
                height / 2f + expandedTextSize / 4f
            } else {
                0f
            }

    private fun calculateTextSize(textSize: Float): Float {
        val initialSize = paint.textSize

        paint.textSize = textSize
        val result = with(paint.fontMetrics) { descent - ascent }

        paint.textSize = initialSize
        return result
    }

    private fun recalculate() {
        currentTextSize = lerp(collapsedTextSize, expandedTextSize, fraction)
        currentColor = calculateColor()
        currentY = calculateY()

        paint.textSize = currentTextSize
        paint.color = currentColor

        ViewCompat.postInvalidateOnAnimation(view)
    }

    private fun calculateColor(): Int {
        return when {
            hasError -> {
                errorColor
            }
            (fraction == 0f || fraction == 1f) && !hasFocus -> {
                unFocusedColor
            }
            else -> {
                blendColors(focusedColor, unFocusedColor, fraction)
            }
        }
    }

    private fun calculateY(): Float = lerp(collapsedY, expandedY, fraction)

    fun draw(canvas: Canvas) {
        canvas.drawText(text, 0, text.length, currentX, currentY, paint)
    }

    private fun lerp(startValue: Float, endValue: Float, fraction: Float): Float {
        return startValue + fraction * (endValue - startValue)
    }

    private fun blendColors(from: Int, to: Int, ratio: Float): Int {
        val inverseRatio = 1f - ratio
        val a = Color.alpha(from) * inverseRatio + Color.alpha(to) * ratio
        val r = Color.red(from) * inverseRatio + Color.red(to) * ratio
        val g = Color.green(from) * inverseRatio + Color.green(to) * ratio
        val b = Color.blue(from) * inverseRatio + Color.blue(to) * ratio
        return Color.argb(a.toInt(), r.toInt(), g.toInt(), b.toInt())
    }
}