package org.booleanull.pushalerts.handler

import org.booleanull.core_ui.base.BaseScreen
import org.booleanull.core_ui.handler.NavigationDeepLinkHandler
import org.booleanull.feature_alarm_ui.screen.AlarmScreen
import org.booleanull.feature_home_ui.screen.HomeScreen
import org.booleanull.feature_home_ui.screen.SettingsScreen
import org.booleanull.feature_onboarding_ui.screen.OnboardingScreen

class ApplicationNavigationDeepLinkHandler : NavigationDeepLinkHandler {

    override fun resolveScreen(screen: String): BaseScreen? {
        return when (screen) {
            NavigationDeepLinkHandler.HOME_FRAGMENT -> HomeScreen()
            NavigationDeepLinkHandler.SETTINGS_FRAGMENT -> SettingsScreen()
            NavigationDeepLinkHandler.ONBOARDING_FRAGMENT -> OnboardingScreen()
            NavigationDeepLinkHandler.ALARM_FRAGMENT -> AlarmScreen()
            else -> null
        }
    }
}