package com.booleanull.feature_home.interactor

import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.functional.Either
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.AlarmWithFilter
import com.booleanull.feature_home.data.toAlarmWithFilterDTO

class InsertAlarm(private val alarmRepository: AlarmGateway) :
    BaseUseCase<Any, InsertAlarm.Params>() {

    override suspend fun run(params: Params?): Any {
        checkNotNull(params)
        alarmRepository.insertAlarm(params.alarmWithFilter.toAlarmWithFilterDTO())
        return 0
    }

    data class Params(
        val alarmWithFilter: AlarmWithFilter
    )
}