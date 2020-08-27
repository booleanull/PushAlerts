package org.booleanull.core.permission

import android.content.Context

class PermissionCompositeControllerImpl(context: Context) : PermissionCompositeController {

    private val permissionControllers =
        arrayOf(
            PermissionAlertController(context),
            PermissionNotificationController(context),
            PermissionChineController(context)
        )

    override fun getPermissionStatus(): PermissionStatus {
        return permissionControllers.map { it.getPermissionStatus() }
            .firstOrNull { it is PermissionStatus.PermissionBadStatus }
            ?: PermissionStatus.PermissionOkStatus
    }

    override fun requestPermission(): List<PermissionIntent> {
        val requests = mutableListOf<PermissionIntent>()
        permissionControllers.map { controller ->
            requests.addAll(controller.requestPermission())
        }
        return requests
    }

    override fun getPermissionStatus(id: Int): PermissionStatus {
        return permissionControllers[id].getPermissionStatus()
    }

    override fun requestPermission(id: Int): List<PermissionIntent> {
        return permissionControllers[id].requestPermission()
    }

    companion object {
        const val PERMISSION_ALERT = 0
        const val PERMISSION_NOTIFICATION = 1
        const val PERMISSION_CHINE = 2
    }
}