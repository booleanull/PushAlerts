package org.booleanull.core.permission

sealed class PermissionStatus(val status: Int) {

    object PermissionOkStatus : PermissionStatus(STATUS_OK)

    data class PermissionBadStatus(val message: String) : PermissionStatus(STATUS_BAD)

    object PermissionNoneStatus : PermissionStatus(STATUS_NONE)

    companion object {
        const val STATUS_OK = 0
        const val STATUS_BAD = 1
        const val STATUS_NONE = 2
    }
}