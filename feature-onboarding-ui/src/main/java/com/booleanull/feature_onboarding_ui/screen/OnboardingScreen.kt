package com.booleanull.feature_onboarding_ui.screen

import androidx.fragment.app.Fragment
import com.booleanull.core_ui.base.BaseScreen
import com.booleanull.feature_onboarding_ui.fragment.OnboardingFragment

class OnboardingScreen : BaseScreen() {

    override fun getFragment(): Fragment {
        return OnboardingFragment()
    }
}