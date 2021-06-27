package org.booleanull.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.booleanull.core.entity.Alarm
import org.booleanull.core.entity.Filter
import org.booleanull.database.dao.AlarmDao

@Database(entities = [Alarm::class, Filter::class], version = 3)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}