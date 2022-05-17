package org.booleanull.repositories

import org.booleanull.core.entity.AlarmWithFilter
import org.booleanull.core.entity.Filter
import org.booleanull.core.functional.Task
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.database.dao.AlarmDao

class AlarmRepositoryImpl(private val alarmDao: AlarmDao) : AlarmRepository {

    override suspend fun getAlarms(): List<AlarmWithFilter> {
        return alarmDao.getAlarms()
    }

    override suspend fun searchAlarm(packageName: String): Task<Exception, AlarmWithFilter> {
        val alarm = alarmDao.search(packageName)
        return if (alarm != null) {
            Task.Success(alarm)
        } else {
            Task.Failure(
                IllegalArgumentException("Application alarm with this package name not found")
            )
        }
    }

    override suspend fun incrementCountAlarm(packageName: String) {
        val alarm = alarmDao.search(packageName)?.alarm ?: return
        alarmDao.insert(alarm.copy(count = alarm.count + 1))
    }

    override suspend fun insertAlarm(alarmWithFilter: AlarmWithFilter) {
        alarmDao.insert(alarmWithFilter)
    }

    override suspend fun insertFilter(filter: Filter) {
        alarmDao.insert(filter)
    }

    override suspend fun removeFilter(filter: Filter) {
        alarmDao.remove(filter)
    }

    override suspend fun clear() {
        alarmDao.clear()
    }
}