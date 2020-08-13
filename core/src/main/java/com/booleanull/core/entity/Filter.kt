package com.booleanull.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Alarm::class,
        parentColumns = ["packageName"],
        childColumns = ["package"],
        onDelete = ForeignKey.CASCADE
    )],
    primaryKeys = ["package", "filter"]
)
data class Filter(
    @ColumnInfo(name = "package")
    val packageName: String,
    @ColumnInfo(name = "filter")
    val filter: String
)