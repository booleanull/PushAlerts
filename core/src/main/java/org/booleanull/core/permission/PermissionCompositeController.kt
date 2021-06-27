package org.booleanull.core.permission

interface PermissionCompositeController : PermissionController {

    fun getPermissionStatus(id: Int): PermissionStatus

    fun requestPermission(id: Int): List<PermissionIntent>

    companion object {
        const val PERMISSION_ALERT = 0
        const val PERMISSION_NOTIFICATION = 1
        const val PERMISSION_CHINE = 2
    }
}