package com.booleanull.feature_home.interactor

import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.functional.Task
import com.booleanull.core.functional.map
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.AlarmWithFilter
import com.booleanull.feature_home.data.toAlarmWithFilter

class SearchAlarmUseCase(private val alarmRepository: AlarmGateway) :
    BaseUseCase<Task<Exception, AlarmWithFilter>, SearchAlarmUseCase.Params>() {

    override suspend fun run(params: Params?): Task<Exception, AlarmWithFilter> {
        checkNotNull(params)
        return alarmRepository.searchAlarm(params.packageName).map { it.toAlarmWithFilter().apply { filters.sortedBy { it.filter } } }
    }

    data class Params(
        val packageName: String
    )
}