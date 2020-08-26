package com.booleanull.feature_home.interactor

import com.booleanull.core.entity.Filter
import com.booleanull.core.interactor.CoroutineUseCase
import com.booleanull.core.repository.AlarmRepository

class InsertFilterUseCase(private val alarmRepository: AlarmRepository) :
    CoroutineUseCase<Unit, InsertFilterUseCase.Params>() {

    override suspend fun run(params: Params?) {
        checkNotNull(params)
        alarmRepository.insertFilter(params.filter)
    }

    data class Params(
        val filter: Filter
    )
}