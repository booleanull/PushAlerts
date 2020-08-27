package org.booleanull.core.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import org.booleanull.core.R

class PermissionAlertController(private val context: Context) : PermissionController {

    override fun getPermissionStatus(): PermissionStatus {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.packageManager.queryIntentActivities(
                    getIntent(),
                    PackageManager.MATCH_DEFAULT_ONLY
                ).isNotEmpty() && Settings.canDrawOverlays(context)
            ) {
                PermissionStatus.PermissionOkStatus
            } else {
                PermissionStatus.PermissionBadStatus(context.getString(R.string.permission_alert_error))
            }
        } else {
            PermissionStatus.PermissionOkStatus
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun requestPermission(): List<PermissionIntent> {
        return mutableListOf(
            PermissionIntent(
                getPermissionStatus(),
                getIntent()
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getIntent(): Intent {
        return Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
    }
}