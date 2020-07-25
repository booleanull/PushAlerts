package com.booleanull.core_ui.handler

import com.booleanull.core_ui.base.BaseScreen

interface NavigationDeepLinkHandler {

    fun resolveScreen(screen: String): BaseScreen?
}