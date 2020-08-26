package org.booleanull.core.facade

import android.content.Context

interface ThemeFacade {

    fun setCurrentTheme(context: Context)

    fun getCurrentTheme(): Int

    companion object {
        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
    }
}