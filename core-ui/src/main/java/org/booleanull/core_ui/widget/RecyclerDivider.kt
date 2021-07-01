package org.booleanull.core_ui.widget

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class RecyclerDivider(
    private val margin: Int = 0,
    private val line: Line
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

        outRect.bottom = margin
        outRect.top = margin
        outRect.left = margin
        outRect.right = margin
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        c.save()

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val width = child.measuredWidth.toFloat()
            val height = child.y + child.measuredHeight - line.height + margin
            c.drawRect(
                line.left,
                height,
                width + line.right,
                height + line.height,
                paint
            )
        }

        c.restore()
    }

    data class Line(
        val left: Float,
        val right: Float,
        val height: Float,
        val color: Int
    )
}