package com.booleanull.pushalerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.core.permission.PermissionController
import com.booleanull.core_ui.base.BaseAppNavigator
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.feature_alarm_ui.fragment.AlarmFragment
import com.booleanull.feature_home_ui.screen.HomeScreen
import com.booleanull.feature_onboarding_ui.screen.OnboardingScreen
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder

class MainFragment : BaseFragment() {

    private val navigatorHolder: NavigatorHolder by inject()

    private val permissionController: PermissionController by inject()

    private val navigator by lazy {
        BaseAppNavigator(requireContext(), requireActivity(), childFragmentManager, R.id.container)
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
        if (childFragmentManager.fragments.isEmpty() && arguments?.getString("deeplink") == null) {
            when {
                !permissionController.getPermissionStatus().status -> {
                    router.navigateChain(
                        HomeScreen(),
                        0,
                        0,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        OnboardingScreen()
                    )
                }
                else -> {
                    router.replace(HomeScreen())
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        if ((childFragmentManager.fragments.lastOrNull() as? BaseFragment)?.onBackPressed() != false) {
            return true
        }
        return false
    }

    fun onDeepLinkNavigate(deepLink: String) {
        if (childFragmentManager.fragments.lastOrNull() is AlarmFragment) {
            router.replace(router.resolve(deepLink))
        } else {
            if (childFragmentManager.fragments.isEmpty()) {
                router.navigateChain(
                    HomeScreen(),
                    0,
                    0,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    router.resolve(deepLink)
                )
            } else {
                router.navigateTo(router.resolve(deepLink))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}