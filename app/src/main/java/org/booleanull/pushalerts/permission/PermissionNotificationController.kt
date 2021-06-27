package org.booleanull.pushalerts.permission

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import org.booleanull.core.R
import org.booleanull.core.permission.PermissionController
import org.booleanull.core.permission.PermissionIntent
import org.booleanull.core.permission.PermissionStatus

class PermissionNotificationController(private val context: Context) : PermissionController {

    override fun getPermissionStatus(): PermissionStatus {
        return if (NotificationManagerCompat.getEnabledListenerPackages(context)
                .contains(context.packageName)
        ) {
            PermissionStatus.PermissionOkStatus
        } else {
            PermissionStatus.PermissionBadStatus(context.getString(R.string.permission_notification_error))
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun requestPermission(): List<PermissionIntent> {
        return listOf(
            PermissionIntent(
                getPermissionStatus(),
                Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            )
        )
    }
}