package com.booleanull.feature_home.interactor

import com.booleanull.core.entity.AlarmWithFilter
import com.booleanull.core.functional.Task
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.core.repository.AlarmRepository

class SearchAlarmUseCase(private val alarmRepository: AlarmRepository) :
    BaseUseCase<Task<Exception, AlarmWithFilter>, SearchAlarmUseCase.Params>() {

    override suspend fun run(params: Params?): Task<Exception, AlarmWithFilter> {
        checkNotNull(params)
        return alarmRepository.searchAlarm(params.packageName)
    }

    data class Params(
        val packageName: String
    )
}