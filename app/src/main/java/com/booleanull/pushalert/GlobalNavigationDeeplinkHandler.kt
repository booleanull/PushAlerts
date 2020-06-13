package com.booleanull.pushalert

import com.booleanull.core_ui.base.BaseScreen
import com.booleanull.core_ui.handler.NavigationDeeplinkHandler
import com.booleanull.feature_home_ui.screen.HomeScreen
import com.booleanull.feature_onboarding_ui.screen.OnboardingScreen

class GlobalNavigationDeeplinkHandler: NavigationDeeplinkHandler {

    override fun resolveScreen(screen: String): BaseScreen? {
        return when(screen) {
            NavigationDeeplinkHandler.homeFragment -> HomeScreen()
            NavigationDeeplinkHandler.onboardingFragment -> OnboardingScreen()
            else -> null
        }
    }
}