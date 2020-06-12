package com.booleanull.feature_home_ui.screen

import androidx.fragment.app.Fragment
import com.booleanull.core_ui.base.BaseScreen
import com.booleanull.feature_home_ui.fragment.HomeFragment

class HomeScreen : BaseScreen() {

    override fun getFragment(): Fragment {
        return HomeFragment()
    }
}