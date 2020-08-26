package org.booleanull.feature_home.interactor

import org.booleanull.core.entity.AlarmWithFilter
import org.booleanull.core.functional.Task
import org.booleanull.core.interactor.CoroutineUseCase
import org.booleanull.core.repository.AlarmRepository

class SearchAlarmUseCase(private val alarmRepository: AlarmRepository) :
    CoroutineUseCase<Task<Exception, AlarmWithFilter>, SearchAlarmUseCase.Params>() {

    override suspend fun run(params: Params?): Task<Exception, AlarmWithFilter> {
        checkNotNull(params)
        return alarmRepository.searchAlarm(params.packageName)
    }

    data class Params(
        val packageName: String
    )
}