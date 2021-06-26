package org.booleanull.core.permission

sealed class PermissionStatus {

    object PermissionOkStatus : PermissionStatus()

    data class PermissionBadStatus(val message: String) : PermissionStatus()

    object PermissionNoneStatus : PermissionStatus()
}