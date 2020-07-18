package com.booleanull.core_ui.base

import androidx.fragment.app.Fragment
import com.booleanull.core_ui.handler.BackPressHandler
import org.koin.android.ext.android.inject

open class BaseFragment : Fragment(), BackPressHandler {

    val router: BaseRouter by inject()

    override fun onBackPressed(): Boolean {
        router.back()
        return true
    }
}