package org.booleanull.core.permission

interface PermissionCompositeController : PermissionController {

    fun getPermissionStatus(id: Int): PermissionStatus

    fun requestPermission(id: Int): List<PermissionIntent>
}