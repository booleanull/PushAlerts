package com.booleanull.pushalert

import androidx.fragment.app.Fragment
import com.booleanull.core_ui.base.BaseScreen

class AlarmScreen : BaseScreen() {

    override fun getFragment(): Fragment {
        return AlarmFragment()
    }
}