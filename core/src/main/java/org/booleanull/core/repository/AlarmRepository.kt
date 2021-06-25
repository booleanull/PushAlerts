package org.booleanull.core.repository

import org.booleanull.core.entity.AlarmWithFilter
import org.booleanull.core.entity.Filter
import org.booleanull.core.functional.Task

interface AlarmRepository {

    suspend fun getAlarms(): List<AlarmWithFilter>

    suspend fun searchAlarm(packageName: String): Task<Exception, AlarmWithFilter>

    suspend fun insertAlarm(alarmWithFilter: AlarmWithFilter)

    suspend fun insertFilter(filter: Filter)

    suspend fun removeFilter(filter: Filter)

    suspend fun clear()
}