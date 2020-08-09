package com.booleanull.pushalerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.core.permission.PermissionController
import com.booleanull.core_ui.base.BaseAppNavigator
import com.booleanull.core_ui.base.BaseFragment
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
                        R.anim.fade_enter,
                        R.anim.fade_exit,
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
        router.replace(router.resolve(deepLink))
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