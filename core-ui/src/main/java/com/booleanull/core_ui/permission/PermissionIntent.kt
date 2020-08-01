package com.booleanull.core_ui.permission

import android.content.Intent

data class PermissionIntent(val permissionStatus: PermissionStatus, val intent: Intent?)