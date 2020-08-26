package com.booleanull.pushalerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.core.permission.PermissionCompositeController
import com.booleanull.core.permission.PermissionStatus
import com.booleanull.core_ui.base.AppNavigator
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import com.booleanull.core_ui.handler.ScreenDeepLinkHandler
import com.booleanull.feature_alarm_ui.fragment.AlarmFragment
import com.booleanull.feature_home_ui.screen.HomeScreen
import com.booleanull.feature_onboarding_ui.screen.OnboardingScreen
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder

class MainFragment : BaseFragment() {

    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator by lazy {
        AppNavigator(requireContext(), requireActivity(), childFragmentManager, R.id.container)
    }

    private val permissionCompositeController: PermissionCompositeController by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.fragments.isEmpty() && arguments?.getString(
                NavigationDeepLinkHandler.DEEP_LINK
            ) == null
        ) {
            if (permissionCompositeController.getPermissionStatus() is PermissionStatus.PermissionBadStatus) {
                router.navigateChain(
                    HomeScreen(),
                    0,
                    0,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    OnboardingScreen()
                )
            } else {
                router.replace(HomeScreen())
            }
        }
    }

    override fun onBackPressed(): Boolean {
        if ((childFragmentManager.fragments.lastOrNull() as? BaseFragment)?.onBackPressed() != false) {
            return true
        }
        return false
    }

    internal fun onDeepLinkNavigate(deepLink: String) {
        if (childFragmentManager.fragments.lastOrNull() is AlarmFragment) {
            router.replace(router.resolve(deepLink))
        } else {
            if (childFragmentManager.fragments.isEmpty()) {
                val screen = router.resolve(deepLink)
                if (screen is ScreenDeepLinkHandler) {
                    screen.resolve(router)
                } else {
                    router.replace(router.resolve(deepLink))
                }
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

    companion object {
        const val TAG = "main_fragment"
    }
}