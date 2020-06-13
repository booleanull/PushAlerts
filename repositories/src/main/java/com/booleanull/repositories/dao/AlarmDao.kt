package com.booleanull.repositories.dao

import androidx.room.*
import com.booleanull.core.dto.AlarmDTO
import com.booleanull.core.dto.AlarmWithFilterDTO
import com.booleanull.core.dto.FilterDTO

@Dao
interface AlarmDao {

    @Transaction @Query("SELECT * from alarm WHERE packageName = :packageName")
    suspend fun search(packageName: String): AlarmWithFilterDTO?

    @Transaction
    suspend fun insert(alarmWithFilterDTO: AlarmWithFilterDTO) {
        insert(alarmWithFilterDTO.alarm)
        alarmWithFilterDTO.filters.forEach {
            insert(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmDTO: AlarmDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filterDTO: FilterDTO)

    @Delete
    suspend fun remove(filterDTO: FilterDTO)
}