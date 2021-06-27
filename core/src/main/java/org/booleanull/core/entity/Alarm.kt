package org.booleanull.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class Alarm(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "packageName")
    val packageName: String,
    val hasAlarm: Boolean,
    val hasFilter: Boolean,
    val isFavorite: Boolean = false,
    val count: Int = 0
)