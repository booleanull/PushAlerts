package com.booleanull.repositories

import com.booleanull.core.dto.AlarmWithFilterDTO
import com.booleanull.core.dto.FilterDTO
import com.booleanull.core.functional.Either
import com.booleanull.core.gateway.AlarmGateway
import java.lang.Exception
import java.lang.IllegalArgumentException

class AlarmRepository(private val applicationDatabase: ApplicationDatabase) :
    AlarmGateway {

    override suspend fun searchAlarm(packageName: String): Either<Exception, AlarmWithFilterDTO> {
        val alarm = applicationDatabase.alarmDao().search(packageName)
        return if (alarm != null) {
            Either.Success(alarm)
        } else {
            Either.Failure(IllegalArgumentException("Application alarm with this package name not found"))
        }
    }

    override suspend fun insertAlarm(alarmWithFilterDTO: AlarmWithFilterDTO) {
        applicationDatabase.alarmDao().insert(alarmWithFilterDTO)
    }

    override suspend fun removeFilter(filterDTO: FilterDTO) {
        applicationDatabase.alarmDao().remove(filterDTO)
    }
}