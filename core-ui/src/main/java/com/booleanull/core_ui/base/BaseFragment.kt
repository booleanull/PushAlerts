package com.booleanull.core_ui.base

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.booleanull.core_ui.handler.BackPressHandler
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment(), BackPressHandler {

    val router: Router by inject()

    override fun onBackPressed(): Boolean {
        router.back()
        return true
    }

    fun showKeyboard(editText: EditText) {
        Handler(Looper.getMainLooper()).postDelayed({
            editText.requestFocus()
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                editText,
                InputMethodManager.SHOW_IMPLICIT
            )
        }, 400L)
    }

    fun hideKeyboard(editText: EditText) {
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            editText.windowToken,
            0
        )
    }
}