package org.booleanull.core.facade

interface SettingsFacade {

    fun getAlarm(): Boolean

    fun getTheme(): Boolean

    companion object {
        const val PREFERENCE_SWITCH_ALARM = "alarm"
        const val PREFERENCE_SWITCH_THEME = "theme"

        const val PREFERENCE_PERMISSIONS = "permissions"
        const val PREFERENCE_PROBLEM = "problem"
        const val PREFERENCE_MARK = "mark"
        const val PREFERENCE_CLEAR = "clear"
    }
}