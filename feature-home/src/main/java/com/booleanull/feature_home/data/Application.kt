package com.booleanull.feature_home.data

import android.graphics.drawable.Drawable
import com.booleanull.core.dto.ApplicationDTO

data class Application(val name: String, val packageName: String, val icon: Drawable)

fun ApplicationDTO.toApplication() =
    Application(
        name,
        packageName,
        icon
    )