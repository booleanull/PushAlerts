package com.booleanull.repositories

import com.booleanull.core.dto.AlarmWithFilterDTO
import com.booleanull.core.dto.FilterDTO
import com.booleanull.core.functional.Task
import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.database.ApplicationDatabase
import java.lang.Exception
import java.lang.IllegalArgumentException

class AlarmRepository(private val applicationDatabase: ApplicationDatabase) :
    AlarmGateway {

    override suspend fun searchAlarm(packageName: String): Task<Exception, AlarmWithFilterDTO> {
        val alarm = applicationDatabase.alarmDao().search(packageName)
        return if (alarm != null) {
            Task.Success(alarm)
        } else {
            Task.Failure(IllegalArgumentException("Application alarm with this package name not found"))
        }
    }

    override suspend fun insertAlarm(alarmWithFilterDTO: AlarmWithFilterDTO) {
        applicationDatabase.alarmDao().insert(alarmWithFilterDTO)
    }

    override suspend fun removeFilter(filterDTO: FilterDTO) {
        applicationDatabase.alarmDao().remove(filterDTO)
    }
}