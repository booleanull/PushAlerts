package com.booleanull.feature_home.interactor

import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.functional.Either
import com.booleanull.core.functional.map
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.AlarmWithFilter
import com.booleanull.feature_home.data.toAlarmWithFilter

class SearchAlarm(private val alarmRepository: AlarmGateway) :
    BaseUseCase<Either<Exception, AlarmWithFilter>, SearchAlarm.Params>() {

    override suspend fun run(params: Params?): Either<Exception, AlarmWithFilter> {
        checkNotNull(params)
        return alarmRepository.searchAlarm(params.packageName).map { it.toAlarmWithFilter() }
    }

    data class Params(
        val packageName: String
    )
}