package ru.vstu.immovables.utils

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Mekamello on 14.12.17.
 */
class VerticalDividerDecoration private constructor(
        private val defaultDivider: Drawable?,
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        val dividers: Map<Int, Drawable?>,
        private val leftPadding: Int,
        private val rightPadding: Int,
        private val drawForLastItem: Boolean) : RecyclerView.ItemDecoration() {


    class Builder(private val defaultDivider: Drawable? = null) {

        private val dividers = mutableMapOf<Int, Drawable?>()
        private var leftPadding: Int = 0
        private var rightPadding: Int = 0
        private var drawForLastItem: Boolean = true

        fun disableDividerForItemAt(index: Int): Builder {
            dividers.put(index, null)
            return this
        }

        fun setDividerForItemAt(index: Int, divider: Drawable): Builder {
            dividers.put(index, divider)
            return this
        }

        fun setPadding(leftPadding: Int, rightPadding: Int): Builder {
            this.leftPadding = leftPadding
            this.rightPadding = rightPadding
            return this
        }

        fun drawForLastItem(enabled: Boolean): Builder {
            this.drawForLastItem = enabled
            return this
        }

        fun build(): VerticalDividerDecoration {
            return VerticalDividerDecoration(
                    defaultDivider,
                    dividers,
                    leftPadding,
                    rightPadding,
                    drawForLastItem
            )
        }

    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        val left = parent.paddingLeft + leftPadding
        val right = parent.width - parent.paddingRight - rightPadding
        for (i in 0..parent.childCount - 1) {
            val view = parent.getChildAt(i)
            val divider = getDividerForView(view, state)?.mutate()
            if (divider != null) {
                val params = view.layoutParams as RecyclerView.LayoutParams
                val bottomMargin = params.bottomMargin
                val top = (view.bottom + bottomMargin + view.translationY).toInt()
                val bottom = top + divider.intrinsicHeight
                divider.alpha = (255 * view.alpha).toInt()
                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView?, state: RecyclerView.State) {
        val divider = getDividerForView(view, state)
        outRect.set(0, 0, 0, divider?.intrinsicHeight ?: 0)
    }

    private fun getDividerForView(view: View, state: RecyclerView.State) = getDividerForPosition(getViewPosition(view), state)

    private fun getViewPosition(view: View) = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

    private fun getDividerForPosition(index: Int, state: RecyclerView.State): Drawable? {
        return when {
            !drawForLastItem && index == state.itemCount - 1 -> null
            dividers.containsKey(index) -> dividers[index]
            else -> defaultDivider
        }
    }
}
