package org.booleanull.core.permission

interface PermissionController {

    fun getPermissionStatus(): PermissionStatus

    fun requestPermission(): List<PermissionIntent>
}