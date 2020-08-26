package com.booleanull.core_ui.handler

import com.booleanull.core_ui.base.Router

interface ScreenDeepLinkHandler {

    fun resolve(router: Router)
}