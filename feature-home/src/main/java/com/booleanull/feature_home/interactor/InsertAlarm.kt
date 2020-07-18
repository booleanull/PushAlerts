package com.booleanull.feature_home.interactor

import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.AlarmWithFilter
import com.booleanull.feature_home.data.toAlarmWithFilterDTO

class InsertAlarm(private val alarmRepository: AlarmGateway) :
    BaseUseCase<Unit, InsertAlarm.Params>() {

    override suspend fun run(params: Params?) {
        checkNotNull(params)
        alarmRepository.insertAlarm(params.alarmWithFilter.toAlarmWithFilterDTO())
    }

    data class Params(
        val alarmWithFilter: AlarmWithFilter
    )
}