package com.booleanull.core.permission

import android.content.Context

class DefaultPermissionController(context: Context) : PermissionController {

    private val permissionControllers =
        arrayOf(
            AlertPermissionController(context),
            NotificationPermissionController(context),
            ChineAutoStartPermissionController(context)
        )

    override fun getPermissionStatus(): PermissionStatus {
        return permissionControllers.map { it.getPermissionStatus() }
            .firstOrNull { it is PermissionBadStatus }
            ?: PermissionOkStatus
    }

    override fun requestPermission(): List<PermissionIntent> {
        val requests = mutableListOf<PermissionIntent>()
        permissionControllers.map { controller ->
            requests.addAll(controller.requestPermission())
        }
        return requests
    }
}