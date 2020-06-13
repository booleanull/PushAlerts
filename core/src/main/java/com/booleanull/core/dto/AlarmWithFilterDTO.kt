package com.booleanull.core.dto

import androidx.room.Embedded
import androidx.room.Relation

data class AlarmWithFilterDTO(
    @Embedded
    val alarm: AlarmDTO,
    @Relation(parentColumn = "packageName", entity = FilterDTO::class, entityColumn = "package")
    val filters: Set<FilterDTO>
)