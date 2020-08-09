package com.booleanull.core_ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import com.booleanull.core_ui.R
import com.booleanull.core_ui.getAttributeColor

class PulseView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getAttributeColor(R.attr.colorAccent)
    }
    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getAttributeColor(R.attr.colorAccent)
    }

    private var animator: ValueAnimator? = null
    private var animator2: ValueAnimator? = null

    private var offset = 0.0f
    private var offset2 = 0.0f

    init {
        start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.alpha = (255 * (1f - offset)).toInt()
        paint2.alpha = (255 * (1f - offset2)).toInt()
        canvas?.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (height / 2) * offset,
            paint
        )
        canvas?.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (height / 2) * offset2,
            paint2
        )
    }

    fun stop() {
        animator?.cancel()
        animator2?.cancel()
        animator = ValueAnimator.ofFloat(offset, 0f).apply {
            duration = 800
            addUpdateListener {
                offset = it.animatedValue as Float
                invalidate()
            }
        }
        animator2 = ValueAnimator.ofFloat(offset2, 0f).apply {
            duration = 800
            addUpdateListener {
                offset2 = it.animatedValue as Float
            }
        }
        animator?.start()
        animator2?.start()
    }

    fun start() {
        offset = 0f
        offset2 = 0f
        invalidate()

        animator?.cancel()
        animator2?.cancel()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1600
            repeatCount = Animation.INFINITE
            addUpdateListener {
                offset = it.animatedValue as Float
                invalidate()
            }
        }
        animator2 = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1600
            repeatCount = Animation.INFINITE
            startDelay = 800
            addUpdateListener {
                offset2 = it.animatedValue as Float
            }
        }
        animator?.start()
        animator2?.start()
    }
}