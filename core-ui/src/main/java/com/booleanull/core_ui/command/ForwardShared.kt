package com.booleanull.core_ui.command

import android.content.Context
import android.os.Build
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.booleanull.core_ui.R
import com.booleanull.core_ui.base.BaseScreen
import ru.terrakok.cicerone.commands.Forward

class ForwardShared(
    screen: BaseScreen,
    private val sharedView: View,
    private val sharedName: String
) : Forward(screen), AnimatedCommand {

    override fun animate(
        context: Context,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction?
    ) {
        fragmentTransaction?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                currentFragment?.sharedElementReturnTransition =
                    TransitionInflater.from(context).inflateTransition(
                        R.transition.default_transition
                    )
                nextFragment?.sharedElementEnterTransition =
                    TransitionInflater.from(context).inflateTransition(
                        R.transition.default_transition
                    )
            }
            it.addSharedElement(sharedView, sharedName)
        }
    }
}