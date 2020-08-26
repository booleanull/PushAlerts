package com.booleanull.core_ui.base

import android.view.View
import com.booleanull.core_ui.command.AnimateForward
import com.booleanull.core_ui.command.SharedViewForward
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

class Router(private val navigationDeepLinkHandler: NavigationDeepLinkHandler) : BaseRouter() {

    fun navigateTo(screen: BaseScreen) {
        executeCommands(Forward(screen))
    }

    fun navigateTo(screen: BaseScreen, sharedView: View, sharedName: String) {
        executeCommands(
            SharedViewForward(
                screen,
                sharedView,
                sharedName
            )
        )
    }

    fun navigateTo(screen: BaseScreen, enter: Int, exit: Int, popEnter: Int, popExit: Int) {
        executeCommands(AnimateForward(screen, enter, exit, popEnter, popExit))
    }

    fun navigateChain(screen: BaseScreen, vararg screens: BaseScreen) {
        executeCommands(Replace(screen))
        screens.forEach {
            executeCommands(Forward(it))
        }
    }

    fun navigateChain(screen: BaseScreen, enter: Int, exit: Int, popEnter: Int, popExit: Int, vararg screens: BaseScreen) {
        executeCommands(Replace(screen))
        screens.forEach {
            executeCommands(AnimateForward(it, enter, exit, popEnter, popExit))
        }
    }

    fun replace(screen: BaseScreen) {
        executeCommands(Replace(screen))
    }

    fun back() {
        executeCommands(Back())
    }

    fun backTo(screen: BaseScreen) {
        executeCommands(BackTo(screen))
    }

    fun resolve(screen: String): BaseScreen {
        return navigationDeepLinkHandler.resolveScreen(screen)
            ?: throw IllegalArgumentException("Incorrect deeplink url")
    }
}
