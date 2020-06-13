package com.booleanull.core_ui.handler

import com.booleanull.core_ui.base.BaseScreen

interface NavigationDeeplinkHandler {

    fun resolveScreen(screen: String): BaseScreen?

    companion object {
        const val homeFragment = "HomeFragment"
        const val onboardingFragment = "OnboardingFragment"
    }
}