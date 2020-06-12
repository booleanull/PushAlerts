package com.booleanull.core_ui.command

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

interface AnimatedCommand {

    fun animate(
        context: Context,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction?
    )
}