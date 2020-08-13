package com.booleanull.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class Alarm(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "packageName")
    val packageName: String,
    val isAlarm: Boolean,
    val isFilter: Boolean
)