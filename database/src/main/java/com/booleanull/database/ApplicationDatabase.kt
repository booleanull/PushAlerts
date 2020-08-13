package com.booleanull.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.booleanull.core.entity.Alarm
import com.booleanull.core.entity.Filter
import com.booleanull.database.dao.AlarmDao

@Database(entities = [Alarm::class, Filter::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}