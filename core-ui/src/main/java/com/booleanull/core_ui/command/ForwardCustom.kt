package com.booleanull.core_ui.command

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.booleanull.core_ui.base.BaseScreen
import ru.terrakok.cicerone.commands.Forward

class ForwardCustom(
    screen: BaseScreen,
    private val enter: Int,
    private val exit: Int,
    private val popEnter: Int,
    private val popExit: Int
) : Forward(screen), AnimatedCommand {

    override fun animate(
        context: Context,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction?
    ) {
        fragmentTransaction?.setCustomAnimations(enter, exit, popEnter, popExit)
    }
}