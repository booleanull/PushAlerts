package com.booleanull.core_ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

data class Line(
    val left: Int,
    val right: Int,
    val height: Int,
    val color: Int
)

class RecyclerDivider(
    private val marginItem: Int = 0,
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

        val margin = View.MeasureSpec.getSize(marginItem)
        val lineHeight = View.MeasureSpec.getSize(line.height)

        outRect.bottom = margin + lineHeight
        outRect.top = margin
        outRect.left = margin
        outRect.right = margin
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        c.save()

        val left = View.MeasureSpec.getSize(line.left).toFloat()
        val right = View.MeasureSpec.getSize(line.right).toFloat()
        val heightLine = View.MeasureSpec.getSize(line.height).toFloat()

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val width = child.measuredWidth.toFloat()
            val margin = View.MeasureSpec.getSize(marginItem)
            val height = child.y + child.measuredHeight - heightLine + margin
            c.drawRect(left, height, width + right, height + heightLine, paint)
        }

        c.restore()
    }
}