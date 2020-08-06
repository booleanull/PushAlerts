package com.booleanull.core_ui

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.ColorInt
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

fun getSpannableClick(string: String, substring: String, onClick: (v: View) -> Unit) : CharSequence {
    return SpannableString(string).apply {
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

fun TextView.setSpannableClick(string: String, substring: String, onClick: (v: View) -> Unit) {
    text = getSpannableClick(string, substring, onClick)
    movementMethod = LinkMovementMethod.getInstance()
}

fun getSpannableLink(string: String, substring: String, link: String) : CharSequence {
    return SpannableString(string).apply {
        setSpan(
            URLSpan(link),
            indexOf(substring),
            indexOf(substring) + substring.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

fun TextView.setSpannableLink(string: String, substring: String, link: String) {
    text = getSpannableLink(string, substring, link)
    movementMethod = LinkMovementMethod.getInstance()
}