package com.booleanull.pushalerts

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.booleanull.core_ui.base.BaseFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (preferences.getBoolean(
                "theme",
                resources
                    .configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            )
        ) {
            setTheme(R.style.AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment(), "MainFragment")
                .commitNow()
        }

        onHandlerIntent(intent)
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
        intent?.extras?.getString("deeplink")?.let { deeplink ->
            (supportFragmentManager.findFragmentByTag("MainFragment") as? MainFragment)?.let { fragment ->
                fragment.arguments = Bundle().apply { putString("deeplink", deeplink) }
                fragment.onDeepLinkNavigate(deeplink)
            }
        }
    }
}
