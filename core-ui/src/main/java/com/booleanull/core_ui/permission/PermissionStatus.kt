package com.booleanull.core_ui.permission

open class PermissionStatus(val status: Boolean)

object PermissionOkStatus: PermissionStatus(true)

data class PermissionBadStatus(val message: String): PermissionStatus(false)