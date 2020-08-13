package com.booleanull.core.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AlarmWithFilter(
    @Embedded
    val alarm: Alarm,
    @Relation(parentColumn = "packageName", entity = Filter::class, entityColumn = "package")
    val filters: Set<Filter>
)