package com.booleanull.core.repository

import com.booleanull.core.entity.AlarmWithFilter
import com.booleanull.core.entity.Filter
import com.booleanull.core.functional.Task

interface AlarmRepository {

    suspend fun searchAlarm(packageName: String): Task<Exception, AlarmWithFilter>

    suspend fun insertAlarm(alarmWithFilter: AlarmWithFilter)

    suspend fun insertFilter(filter: Filter)

    suspend fun removeFilter(filter: Filter)

    suspend fun clear()
}