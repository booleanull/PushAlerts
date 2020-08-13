package com.booleanull.pushalerts

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import com.booleanull.core_ui.helper.ThemeManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.setCurrentTheme(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment(), MainFragment.TAG)
                .commitNow()

            onHandlerIntent(intent)
        }
    }

    override fun onBackPressed() {
        (supportFragmentManager.fragments.firstOrNull() as? BaseFragment)?.onBackPressed()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
        onHandlerIntent(intent)
    }

    private fun onHandlerIntent(intent: Intent?) {
        intent?.extras?.getString(NavigationDeepLinkHandler.DEEP_LINK)?.let { deepLink ->
            (supportFragmentManager.findFragmentByTag(MainFragment.TAG) as? MainFragment)?.let { fragment ->
                fragment.arguments =
                    Bundle().apply { putString(NavigationDeepLinkHandler.DEEP_LINK, deepLink) }
                fragment.onDeepLinkNavigate(deepLink)
            }
        }
    }
}
