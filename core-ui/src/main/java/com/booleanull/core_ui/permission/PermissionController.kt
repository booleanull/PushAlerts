package com.booleanull.core_ui.permission

interface PermissionController {

    fun getPermissionStatus(): PermissionStatus

    fun requestPermission(): List<PermissionIntent>
}