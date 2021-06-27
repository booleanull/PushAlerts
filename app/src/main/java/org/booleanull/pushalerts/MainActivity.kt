package org.booleanull.pushalerts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.booleanull.core.facade.ThemeFacade
import org.booleanull.core_ui.base.BaseFragment
import org.booleanull.core_ui.handler.NavigationDeepLinkHandler
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val themeFacade: ThemeFacade by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        themeFacade.setCurrentTheme(this)
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
        if ((getIntent().flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) == 0) {
            intent?.extras?.getString(NavigationDeepLinkHandler.DEEP_LINK)?.let { deepLink ->
                (supportFragmentManager.findFragmentByTag(MainFragment.TAG) as? MainFragment)?.let { fragment ->
                    fragment.arguments =
                        Bundle(1).apply { putString(NavigationDeepLinkHandler.DEEP_LINK, deepLink) }
                    fragment.onDeepLinkNavigate(deepLink)
                }
            }
        }
    }
}
