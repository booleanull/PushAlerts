package com.booleanull.core.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class AlarmDTO(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "packageName")
    val packageName: String,
    val isAlarm: Boolean,
    val isFilter: Boolean
)