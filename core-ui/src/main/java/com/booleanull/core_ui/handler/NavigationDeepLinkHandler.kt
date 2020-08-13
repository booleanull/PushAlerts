package com.booleanull.core_ui.handler

import com.booleanull.core_ui.base.BaseScreen

interface NavigationDeepLinkHandler {

    fun resolveScreen(screen: String): BaseScreen?

    companion object {
        const val DEEP_LINK = "deep_link"
        const val PACKAGE_NAME = "package_name"
        const val HOME_FRAGMENT = "home_fragment"
        const val SETTINGS_FRAGMENT = "settings_fragment"
        const val ONBOARDING_FRAGMENT = "onboarding_fragment"
        const val ALARM_FRAGMENT = "alarm_fragment"
    }
}