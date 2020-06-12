package com.booleanull.sleepork

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.base.ForwardShared
import com.booleanull.feature_home_ui.screen.HomeScreen
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class MainFragment : BaseFragment() {
    private val navigatorHolder: NavigatorHolder by inject()

    private val navigator by lazy {
        object : SupportAppNavigator(requireActivity(), childFragmentManager, R.id.container) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction?
            ) {
                (command as? ForwardShared)?.let { command ->
                    fragmentTransaction?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            currentFragment?.sharedElementReturnTransition =
                                TransitionInflater.from(requireContext()).inflateTransition(
                                    R.transition.default_transition
                                )
                            nextFragment?.sharedElementEnterTransition =
                                TransitionInflater.from(requireContext()).inflateTransition(
                                    R.transition.default_transition
                                )
                        }
                        it.addSharedElement(command.getSharedView(), command.getSharedName())
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            router.replace(HomeScreen())
        }
        progressBar.isVisible = false
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        if((childFragmentManager.fragments.lastOrNull() as? BaseFragment)?.onBackPressed() != false) {
            return true
        }
        return false
    }
}