package com.booleanull.core_ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.booleanull.core_ui.command.AnimateCommand
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class AppNavigator(
    private val context: Context,
    fragmentActivity: FragmentActivity,
    fragmentManager: FragmentManager,
    containerId: Int
) : SupportAppNavigator(fragmentActivity, fragmentManager, containerId) {

    override fun setupFragmentTransaction(
        command: Command?,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction?
    ) {
        if (command is AnimateCommand) {
            command.animate(
                context,
                command,
                currentFragment,
                nextFragment,
                fragmentTransaction
            )
        }
    }
}