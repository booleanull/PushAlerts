package org.booleanull.feature_home_ui.screen

import androidx.fragment.app.Fragment
import org.booleanull.core_ui.base.BaseScreen
import org.booleanull.core_ui.base.Router
import org.booleanull.core_ui.handler.ScreenDeepLinkHandler
import org.booleanull.feature_home_ui.fragment.SettingsHolderFragment

class SettingsScreen : BaseScreen(), ScreenDeepLinkHandler {

    override fun getFragment(): Fragment {
        return SettingsHolderFragment()
    }

    override fun resolve(router: Router) {
        router.navigateChain(
            HomeScreen(),
            0,
            0,
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right,
            SettingsScreen()
        )
    }
}