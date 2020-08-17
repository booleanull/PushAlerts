package com.booleanull.pushalerts

import android.content.Context
import com.booleanull.core.facade.SettingsFacade
import com.booleanull.core.facade.ThemeFacade
import com.booleanull.core_ui.R

class ThemeFacadeImpl(
    private val settingsFacade: SettingsFacade
) : ThemeFacade {

    override fun setCurrentTheme(context: Context) {
        if (settingsFacade.getTheme()) {
            context.setTheme(R.style.AppTheme_Dark)
        } else {
            context.setTheme(R.style.AppTheme)
        }
    }

    override fun getCurrentTheme(): Int {
        return if (settingsFacade.getTheme()) {
            ThemeFacade.THEME_DARK
        } else {
            ThemeFacade.THEME_LIGHT
        }
    }
}