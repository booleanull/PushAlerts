package org.booleanull.feature_home.interactor

import org.booleanull.core.interactor.CoroutineUseCase
import org.booleanull.core.repository.AlarmRepository

class IncrementCountAlarmUseCase(private val alarmRepository: AlarmRepository) :
    CoroutineUseCase<Unit, IncrementCountAlarmUseCase.Params>() {

    override suspend fun run(params: Params?) {
        checkNotNull(params)
        return alarmRepository.incrementCountAlarm(params.packageName)
    }

    data class Params(
        val packageName: String
    )
}