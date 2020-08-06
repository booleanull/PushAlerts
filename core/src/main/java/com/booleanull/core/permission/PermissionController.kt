package com.booleanull.core.permission

interface PermissionController {

    fun getPermissionStatus(): PermissionStatus

    fun requestPermission(): List<PermissionIntent>
}