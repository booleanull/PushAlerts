package com.booleanull.feature_home.interactor

import com.booleanull.core.entity.AlarmWithFilter
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.core.repository.AlarmRepository

class InsertAlarmUseCase(private val alarmRepository: AlarmRepository) :
    BaseUseCase<Unit, InsertAlarmUseCase.Params>() {

    override suspend fun run(params: Params?) {
        checkNotNull(params)
        alarmRepository.insertAlarm(params.alarmWithFilter)
    }

    data class Params(
        val alarmWithFilter: AlarmWithFilter
    )
}