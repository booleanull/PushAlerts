package com.booleanull.pushalert

import android.content.ContentResolver
import com.booleanull.core_ui.helper.SettingsListenerHelper

class NotificationListenerHelper(
    packageName: String,
    contentResolver: ContentResolver
) : SettingsListenerHelper(packageName, contentResolver) {

    override fun getSettingName() =
        NOTIFICATION_SETTING_NAME

    companion object {
        const val NOTIFICATION_SETTING_NAME = "enabled_notification_listeners"
    }
}