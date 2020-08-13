package com.booleanull.core_ui.helper

import android.content.Context
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import com.booleanull.core_ui.R

object ThemeManager {

    fun setCurrentTheme(context: Context) {
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
                "theme",
                context.resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            )
        ) {
            context.setTheme(R.style.AppTheme_Dark)
        } else {
            context.setTheme(R.style.AppTheme)
        }
    }

    fun getCurrentTheme(context: Context): Int {
        return if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
                "theme",
                context.resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            )
        ) {
            THEME_DARK
        } else {
            THEME_LIGHT
        }
    }

    const val THEME_LIGHT = 0
    const val THEME_DARK = 1
}