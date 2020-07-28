package com.booleanull.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.booleanull.core.dto.AlarmDTO
import com.booleanull.core.dto.FilterDTO
import com.booleanull.database.dao.AlarmDao

@Database(entities = [AlarmDTO::class, FilterDTO::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}