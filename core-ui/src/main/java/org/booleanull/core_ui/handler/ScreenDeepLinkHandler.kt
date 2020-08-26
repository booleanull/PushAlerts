package org.booleanull.core_ui.handler

import org.booleanull.core_ui.base.Router

interface ScreenDeepLinkHandler {

    fun resolve(router: Router)
}