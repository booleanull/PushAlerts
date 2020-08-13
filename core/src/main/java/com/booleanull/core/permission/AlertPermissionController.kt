package com.booleanull.core.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.booleanull.core.R

class AlertPermissionController(private val context: Context) : PermissionController {

    override fun getPermissionStatus(): PermissionStatus {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(context)) {
                PermissionOkStatus
            } else {
                PermissionBadStatus(context.getString(R.string.alert_permission))
            }
        } else {
            PermissionOkStatus
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun requestPermission(): List<PermissionIntent> {
        val list = mutableListOf(
            PermissionIntent(
                getPermissionStatus(),
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            )
        )

        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.putExtra("extra_pkgname", context.packageName)
        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.PermissionsEditorActivity"
        )

        if (context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .isNotEmpty()
        ) {
            list.add(PermissionIntent(PermissionStatus(false), intent))
        }

        return list
    }
}