package com.booleanull.core_ui.helper

import android.content.ComponentName
import android.content.ContentResolver
import android.content.Intent
import android.database.ContentObserver
import android.provider.Settings
import android.text.TextUtils

abstract class SettingsListenerHelper(
    private val packageName: String,
    private val contentResolver: ContentResolver
) {

    protected abstract fun getSettingName(): String

    fun isSettingsEnabled(): Boolean {
        val flat = Settings.Secure.getString(
            contentResolver,
            getSettingName()
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":".toRegex()).toTypedArray()
            for (i in names.indices) {
                ComponentName.unflattenFromString(names[i])?.let {
                    if (TextUtils.equals(packageName, it.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}