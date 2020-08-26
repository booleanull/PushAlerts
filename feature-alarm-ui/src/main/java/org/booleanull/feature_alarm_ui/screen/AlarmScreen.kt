package org.booleanull.feature_alarm_ui.screen

import androidx.fragment.app.Fragment
import org.booleanull.core_ui.base.BaseScreen
import org.booleanull.feature_alarm_ui.fragment.AlarmFragment

class AlarmScreen : BaseScreen() {

    override fun getFragment(): Fragment {
        return AlarmFragment()
    }
}