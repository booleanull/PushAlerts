package com.booleanull.core.permission

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.booleanull.core.R

class NotificationPermissionController(private val context: Context) : PermissionController {

    override fun getPermissionStatus(): PermissionStatus {
        return if(NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.packageName)) {
            PermissionOkStatus
        } else {
            PermissionBadStatus(context.getString(R.string.notification_permission))
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun requestPermission(): List<PermissionIntent> {
        return listOf(PermissionIntent(getPermissionStatus(), Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)))
    }
}