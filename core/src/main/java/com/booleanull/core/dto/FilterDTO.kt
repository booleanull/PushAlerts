package com.booleanull.core.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.booleanull.core.dto.AlarmDTO

@Entity(
    foreignKeys = [ForeignKey(entity = AlarmDTO::class,
        parentColumns = ["packageName"],
        childColumns = ["package"],
        onDelete = ForeignKey.CASCADE)],
    primaryKeys = ["package", "filter"]
)
data class FilterDTO(
    @ColumnInfo(name = "package")
    val packageName: String,
    @ColumnInfo(name = "filter")
    val filter: String
)