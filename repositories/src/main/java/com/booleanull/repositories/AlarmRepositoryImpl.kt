package com.booleanull.repositories

import com.booleanull.core.entity.AlarmWithFilter
import com.booleanull.core.entity.Filter
import com.booleanull.core.functional.Task
import com.booleanull.core.repository.AlarmRepository
import com.booleanull.database.ApplicationDatabase

class AlarmRepositoryImpl(private val applicationDatabase: ApplicationDatabase) : AlarmRepository {

    override suspend fun searchAlarm(packageName: String): Task<Exception, AlarmWithFilter> {
        val alarm = applicationDatabase.alarmDao().search(packageName)
        return if (alarm != null) {
            Task.Success(alarm)
        } else {
            Task.Failure(
                IllegalArgumentException("Application alarm with this package name not found")
            )
        }
    }

    override suspend fun insertAlarm(alarmWithFilter: AlarmWithFilter) {
        applicationDatabase.alarmDao().insert(alarmWithFilter)
    }

    override suspend fun insertFilter(filter: Filter) {
        applicationDatabase.alarmDao().insert(filter)
    }

    override suspend fun removeFilter(filter: Filter) {
        applicationDatabase.alarmDao().remove(filter)
    }

    override suspend fun clear() {
        applicationDatabase.alarmDao().clear()
    }
}