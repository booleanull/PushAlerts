package org.booleanull.core_ui.widget

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import org.booleanull.core_ui.R
import org.booleanull.core_ui.dp
import org.booleanull.core_ui.getAttributeColor

class PagerIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    @ColorInt
    var activeColor: Int = Color.BLUE
        set(value) {
            field = value
            invalidate()
        }

    @ColorInt
    var disabledColor: Int = Color.WHITE
        set(value) {
            field = value
            invalidate()
        }

    @Dimension
    var sizeActiveCircle: Float = 12f
        set(value) {
            field = value
            requestLayout()
        }

    @Dimension
    var sizeCircle: Float = 8f
        set(value) {
            field = value
            requestLayout()
        }

    @Dimension
    var marginCircle: Float = 4f
        set(value) {
            field = value
            requestLayout()
        }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            active = position
            progress = positionOffset
            invalidate()
        }

        override fun onPageScrollStateChanged(state: Int) = Unit
        override fun onPageSelected(position: Int) = Unit
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val argbEvaluator = ArgbEvaluator()

    private var active: Int = 0
    private var progress: Float = 0f
    private var count: Int = 0
        set(value) {
            field = value
            requestLayout()
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.PagerIndicator, defStyleAttr, 0).apply {
            activeColor = getColor(
                R.styleable.PagerIndicator_activeColor,
                context.getAttributeColor(R.attr.colorAccent)
            )
            disabledColor = getColor(
                R.styleable.PagerIndicator_disabledColor,
                ContextCompat.getColor(context, R.color.colorGraySecondary)
            )
            sizeActiveCircle = getDimension(R.styleable.PagerIndicator_sizeActiveCircle, 12f)
            sizeCircle = getDimension(R.styleable.PagerIndicator_sizeCircle, 8f)
            marginCircle = getDimension(R.styleable.PagerIndicator_marginCircle, 4f)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = dp(sizeActiveCircle) * count + dp(marginCircle) * (count - 1)
        val height = dp(sizeActiveCircle)
        setMeasuredDimension(width.toInt(), height.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0..count) {
            val colorActive = argbEvaluator.evaluate(progress, activeColor, disabledColor) as Int
            val colorDisabled = argbEvaluator.evaluate(progress, disabledColor, activeColor) as Int
            val sizeActive =
                (dp(sizeActiveCircle) - dp(sizeCircle)) * (1f - progress) + dp(sizeCircle)
            val sizeDisabled = (dp(sizeActiveCircle) - dp(sizeCircle)) * progress + dp(sizeCircle)
            paint.color = when {
                i == active -> colorActive
                i - 1 == active -> colorDisabled
                else -> disabledColor
            }
            canvas.drawCircle(
                dp(sizeActiveCircle) / 2 + i * (dp(sizeActiveCircle) + dp(marginCircle)),
                height.toFloat() / 2,
                when {
                    i == active -> sizeActive / 2
                    i - 1 == active -> sizeDisabled / 2
                    else -> dp(sizeCircle) / 2
                },
                paint
            )
        }
    }

    internal fun setViewPager(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(onPageChangeListener)
        count = viewPager.adapter?.count ?: 0
    }

    internal fun removeViewPager(viewPager: ViewPager) {
        viewPager.removeOnPageChangeListener(onPageChangeListener)
    }
}

fun ViewPager.setPagerIndicator(pagerIndicator: PagerIndicator) {
    pagerIndicator.setViewPager(this)
}

fun ViewPager.removePagerIndicator(pagerIndicator: PagerIndicator) {
    pagerIndicator.removeViewPager(this)
}