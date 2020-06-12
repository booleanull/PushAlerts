package com.booleanull.core_ui.base

import android.view.View
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

class BaseRouter : BaseRouter() {

    fun navigateTo(screen: BaseScreen) {
        executeCommands(Forward(screen))
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

    fun navigateTo(screen: BaseScreen, sharedView: View, sharedName: String) {
        executeCommands(
            ForwardShared(
                screen,
                sharedView,
                sharedName
            )
        )
    }
}

class ForwardShared(
    screen: BaseScreen,
    private val sharedView: View,
    private val sharedName: String
) : Forward(screen) {

    fun getSharedView(): View {
        return sharedView
    }

    fun getSharedName(): String {
        return sharedName
    }
}
