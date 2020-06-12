package com.booleanull.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.booleanull.core.dao.AlarmDao
import com.booleanull.core.dto.AlarmDTO
import com.booleanull.core.dto.FilterDTO

@Database(entities = [AlarmDTO::class, FilterDTO::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}