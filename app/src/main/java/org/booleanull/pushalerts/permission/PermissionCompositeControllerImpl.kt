package org.booleanull.pushalerts.permission

import android.content.Context
import org.booleanull.core.permission.PermissionCompositeController
import org.booleanull.core.permission.PermissionIntent
import org.booleanull.core.permission.PermissionStatus

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
}