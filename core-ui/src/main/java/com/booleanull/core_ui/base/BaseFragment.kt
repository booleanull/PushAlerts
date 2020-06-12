package com.booleanull.core_ui.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.booleanull.core_ui.handler.BackPressHandler
import org.koin.android.ext.android.inject

open class BaseFragment : Fragment(),
    BackPressHandler {

    val router: BaseRouter by inject()

    override fun onBackPressed(): Boolean {
        router.back()
        return true
    }

    fun showKeyboard() {
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            0
        )
    }

    fun hideKeyboard() {
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.HIDE_IMPLICIT_ONLY,
            0
        )
    }
}