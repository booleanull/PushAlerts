package org.booleanull.core.permission

import android.content.Intent

data class PermissionIntent(val permissionStatus: PermissionStatus, val intent: Intent?)