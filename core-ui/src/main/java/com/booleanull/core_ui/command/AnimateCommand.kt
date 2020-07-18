package com.booleanull.core_ui.command

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.commands.Command

interface AnimateCommand {

    fun animate(
        context: Context,
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction?
    )
}