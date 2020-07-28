package com.booleanull.pushalert

import com.booleanull.core_ui.base.BaseScreen
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import com.booleanull.feature_home_ui.screen.HomeScreen
import com.booleanull.feature_onboarding_ui.screen.OnboardingScreen

class ApplicationNavigationDeepLinkHandler: NavigationDeepLinkHandler {

    override fun resolveScreen(screen: String): BaseScreen? {
        return when(screen) {
            homeFragment -> HomeScreen()
            onboardingFragment -> OnboardingScreen()
            else -> null
        }
    }

    companion object {
        const val homeFragment = "HomeFragment"
        const val onboardingFragment = "OnboardingFragment"
    }
}