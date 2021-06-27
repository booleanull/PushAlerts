package org.booleanull.core_ui

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun View.dp(value: Float): Float {
    return resources.displayMetrics.density * value
}

fun View.dp(value: Int): Int {
    return dp(value.toFloat()).toInt()
}

fun Fragment.dp(value: Float): Float {
    return view?.dp(value) ?: value
}

fun Fragment.dp(value: Int): Int {
    return view?.dp(value) ?: value
}

fun Context.getAttributeColor(attribute: Int, @ColorInt defColor: Int = Color.RED): Int {
    return theme.obtainStyledAttributes(intArrayOf(attribute)).let { typedArray ->
        try {
            typedArray.getColor(typedArray.getIndex(0), defColor)
        } catch (e: Exception) {
            defColor
        } finally {
            typedArray.recycle()
        }
    }
}

fun Switch.setChecked(status: Boolean, changeListener: CompoundButton.OnCheckedChangeListener) {
    setOnCheckedChangeListener(null)
    isChecked = status
    setOnCheckedChangeListener(changeListener)
}

fun getSpannableClick(
    charSequence: CharSequence,
    substring: String,
    onClick: (v: View) -> Unit
): CharSequence {
    return SpannableString(charSequence).apply {
        setSpan(
            object : ClickableSpan() {
                override fun onClick(v: View) {
                    onClick.invoke(v)
                }
            },
            indexOf(substring),
            indexOf(substring) + substring.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

fun TextView.setSpannableClick(
    charSequence: CharSequence,
    substring: String,
    onClick: (v: View) -> Unit
) {
    text = getSpannableClick(charSequence, substring, onClick)
    movementMethod = LinkMovementMethod.getInstance()
}

fun getSpannableLink(charSequence: CharSequence, substring: String, link: String): CharSequence {
    return SpannableString(charSequence).apply {
        setSpan(
            URLSpan(link),
            indexOf(substring),
            indexOf(substring) + substring.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

fun TextView.setSpannableLink(charSequence: CharSequence, substring: String, link: String) {
    text = getSpannableLink(charSequence, substring, link)
    movementMethod = LinkMovementMethod.getInstance()
}

fun getSpannableBold(
    charSequence: CharSequence,
    substring: String
): CharSequence {
    return SpannableString(charSequence).apply {
        setSpan(
            StyleSpan(Typeface.BOLD),
            indexOf(substring),
            indexOf(substring) + substring.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

fun TextView.setSpannableBold(
    charSequence: CharSequence,
    substring: String
) {
    text = getSpannableBold(charSequence, substring)
}

fun View.setOnLongClickListener(
    duration: Long,
    listenerStart: (() -> Unit)? = null,
    listenerEnd: (() -> Unit)? = null,
    listenerCancel: (() -> Unit)? = null
) {
    setOnTouchListener { view, motionEvent ->
        if (motionEvent?.action == MotionEvent.ACTION_DOWN) {
            listenerStart?.invoke()
            handler.postDelayed({ listenerEnd?.invoke() }, duration)
        } else if (motionEvent?.action == MotionEvent.ACTION_UP) {
            handler.removeCallbacksAndMessages(null)
            listenerCancel?.invoke()
        }
        true
    }
}

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawables.filterNotNull().forEach {
        it.colorFilter =
            PorterDuffColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}