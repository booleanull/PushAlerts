package org.booleanull.pushalerts.facade

import android.content.Context
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import org.booleanull.core.facade.SettingsFacade

class SettingsFacadeImpl(private val context: Context) : SettingsFacade {

    override fun getAlarm(): Boolean {
        return PreferenceManager
            .getDefaultSharedPreferences(context)
            .getBoolean(SettingsFacade.PREFERENCE_SWITCH_ALARM, false)
    }

    override fun getTheme(): Boolean {
        return PreferenceManager
            .getDefaultSharedPreferences(context).getBoolean(
                SettingsFacade.PREFERENCE_SWITCH_THEME, context.resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
            )
    }
}