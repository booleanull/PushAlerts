package org.booleanull.repositories

import org.booleanull.core.entity.AlarmWithFilter
import org.booleanull.core.entity.Filter
import org.booleanull.core.functional.Task
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.database.ApplicationDatabase

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