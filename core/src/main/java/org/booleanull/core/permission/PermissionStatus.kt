package org.booleanull.core.permission

sealed class PermissionStatus {

    object PermissionOkStatus : PermissionStatus()

    data class PermissionBadStatus(val message: String) : PermissionStatus()

    object PermissionNoneStatus : PermissionStatus()

    companion object {
        const val STATUS_OK = 0
        const val STATUS_BAD = 1
        const val STATUS_NONE = 2
    }
}