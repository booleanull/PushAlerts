package com.booleanull.core_ui.helper

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerDivider(
    private val margin: Int = 0,
    private val line: Line,
    private val hasHeader: Boolean = false,
    private val hasFooter: Boolean = false
) :
    RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.color = line.color
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = margin + line.height
        outRect.top = margin
        outRect.left = margin
        outRect.right = margin
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        c.save()

        for (i in (if (hasHeader) 1 else 0) until parent.childCount - 1 - if (hasFooter) 1 else 0) {
            val child = parent.getChildAt(i)
            val width = child.measuredWidth.toFloat()
            val height = child.y + child.measuredHeight - line.height + margin
            c.drawRect(
                line.left.toFloat(),
                height,
                width + line.right.toFloat(),
                height + line.height,
                paint
            )
        }

        c.restore()
    }

    data class Line(
        val left: Int,
        val right: Int,
        val height: Int,
        val color: Int
    )
}