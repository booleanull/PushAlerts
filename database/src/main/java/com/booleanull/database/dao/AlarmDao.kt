package com.booleanull.database.dao

import androidx.room.*
import com.booleanull.core.entity.Alarm
import com.booleanull.core.entity.AlarmWithFilter
import com.booleanull.core.entity.Filter

@Dao
interface AlarmDao {

    @Transaction
    @Query("SELECT * from alarm WHERE packageName = :packageName")
    suspend fun search(packageName: String): AlarmWithFilter?

    @Transaction
    suspend fun insert(alarmWithFilter: AlarmWithFilter) {
        insert(alarmWithFilter.alarm)
        alarmWithFilter.filters.forEach {
            insert(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarm: Alarm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filter: Filter)

    @Delete
    suspend fun remove(filter: Filter)

    @Query("DELETE from alarm")
    suspend fun clear()
}