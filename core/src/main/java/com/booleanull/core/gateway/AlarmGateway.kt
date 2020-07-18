package com.booleanull.core.gateway

import com.booleanull.core.dto.AlarmWithFilterDTO
import com.booleanull.core.dto.FilterDTO
import com.booleanull.core.functional.Task
import java.lang.Exception

interface AlarmGateway {

    suspend fun searchAlarm(packageName: String): Task<Exception, AlarmWithFilterDTO>

    suspend fun insertAlarm(alarmWithFilterDTO: AlarmWithFilterDTO)

    suspend fun removeFilter(filterDTO: FilterDTO)
}