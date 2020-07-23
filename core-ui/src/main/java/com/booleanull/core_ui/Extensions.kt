package com.booleanull.core_ui

import android.content.Context
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import java.lang.Exception

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

fun Context.getColor(attribute: Int, @ColorInt defColor: Int): Int {
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