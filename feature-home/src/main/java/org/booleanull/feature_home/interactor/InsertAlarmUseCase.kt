package org.booleanull.feature_home.interactor

import org.booleanull.core.entity.AlarmWithFilter
import org.booleanull.core.interactor.CoroutineUseCase
import org.booleanull.core.repository.AlarmRepository

class InsertAlarmUseCase(private val alarmRepository: AlarmRepository) :
    CoroutineUseCase<Unit, InsertAlarmUseCase.Params>() {

    override suspend fun run(params: Params?) {
        checkNotNull(params)
        alarmRepository.insertAlarm(params.alarmWithFilter)
    }

    data class Params(
        val alarmWithFilter: AlarmWithFilter
    )
}