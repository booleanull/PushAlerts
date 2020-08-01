package com.booleanull.core_ui.permission

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build

class ChineAutoStartPermissionController(private val context: Context) : PermissionController {

    override fun getPermissionStatus(): PermissionStatus {
        return PermissionStatus(true)
    }

    override fun requestPermission(): List<PermissionIntent> {
        val intent = Intent()
        val manufacturer = Build.MANUFACTURER
        when {
            "xiaomi".equals(manufacturer, ignoreCase = true) -> {
                intent.component = ComponentName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity"
                )
            }
            "oppo".equals(manufacturer, ignoreCase = true) -> {
                intent.component = ComponentName(
                    "com.coloros.safecenter",
                    "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                )
            }
            "vivo".equals(manufacturer, ignoreCase = true) -> {
                intent.component = ComponentName(
                    "com.vivo.permissionmanager",
                    "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                )
            }
            "Letv".equals(manufacturer, ignoreCase = true) -> {
                intent.component = ComponentName(
                    "com.letv.android.letvsafe",
                    "com.letv.android.letvsafe.AutobootManageActivity"
                )
            }
            "Honor".equals(manufacturer, ignoreCase = true) -> {
                intent.component = ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.process.ProtectActivity"
                )
            }
        }

        val list: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return listOf(
            PermissionIntent(
                PermissionStatus(false),
                if (list.isNotEmpty()) intent else null
            )
        )
    }
}