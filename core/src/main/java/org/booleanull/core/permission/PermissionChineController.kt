package org.booleanull.core.permission

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build

class PermissionChineController(private val context: Context) : PermissionController {

    override fun getPermissionStatus(): PermissionStatus {
        val manufacturer = Build.MANUFACTURER
        return if ("xiaomi".equals(manufacturer, ignoreCase = true) ||
            "oppo".equals(manufacturer, ignoreCase = true) ||
            "vivo".equals(manufacturer, ignoreCase = true) ||
            "Letv".equals(manufacturer, ignoreCase = true) ||
            "Honor".equals(manufacturer, ignoreCase = true)
        ) {
            PermissionStatus.PermissionOkStatus
        } else {
            PermissionStatus.PermissionNoneStatus
        }
    }

    override fun requestPermission(): List<PermissionIntent> {
        val permissions = mutableListOf<PermissionIntent>()

        val autoStartIntent = Intent()
        val manufacturer = Build.MANUFACTURER
        when {
            "xiaomi".equals(manufacturer, ignoreCase = true) -> {
                autoStartIntent.component = ComponentName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity"
                )
            }
            "oppo".equals(manufacturer, ignoreCase = true) -> {
                autoStartIntent.component = ComponentName(
                    "com.coloros.safecenter",
                    "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                )
            }
            "vivo".equals(manufacturer, ignoreCase = true) -> {
                autoStartIntent.component = ComponentName(
                    "com.vivo.permissionmanager",
                    "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                )
            }
            "Letv".equals(manufacturer, ignoreCase = true) -> {
                autoStartIntent.component = ComponentName(
                    "com.letv.android.letvsafe",
                    "com.letv.android.letvsafe.AutobootManageActivity"
                )
            }
            "Honor".equals(manufacturer, ignoreCase = true) -> {
                autoStartIntent.component = ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.process.ProtectActivity"
                )
            }
        }

        if (context.packageManager.queryIntentActivities(
                autoStartIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            ).isNotEmpty()
        ) {
            permissions.add(PermissionIntent(getPermissionStatus(), autoStartIntent))
        }

        val permissionIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
        permissionIntent.putExtra("extra_pkgname", context.packageName)
        permissionIntent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.PermissionsEditorActivity"
        )

        if (context.packageManager.queryIntentActivities(
                permissionIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            ).isNotEmpty()
        ) {
            permissions.add(PermissionIntent(getPermissionStatus(), permissionIntent))
        }

        return permissions
    }
}