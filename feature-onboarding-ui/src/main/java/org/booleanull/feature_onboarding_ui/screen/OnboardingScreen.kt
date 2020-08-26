package org.booleanull.feature_onboarding_ui.screen

import androidx.fragment.app.Fragment
import org.booleanull.core_ui.base.BaseScreen
import org.booleanull.feature_onboarding_ui.fragment.OnboardingFragment

class OnboardingScreen : BaseScreen() {

    override fun getFragment(): Fragment {
        return OnboardingFragment()
    }
}