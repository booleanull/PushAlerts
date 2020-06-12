package com.booleanull.core_ui

import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
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

fun Switch.setChecked(status: Boolean, changeListener: CompoundButton.OnCheckedChangeListener) {
    setOnCheckedChangeListener(null)
    isChecked = status
    setOnCheckedChangeListener(changeListener)
}