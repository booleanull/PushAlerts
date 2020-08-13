package com.booleanull.feature_home.interactor

import com.booleanull.core.entity.Filter
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.core.repository.AlarmRepository

class RemoveFilterUseCase(private val alarmRepository: AlarmRepository) :
    BaseUseCase<Unit, RemoveFilterUseCase.Params>() {

    override suspend fun run(params: Params?) {
        checkNotNull(params)
        alarmRepository.removeFilter(params.filter)
    }

    data class Params(
        val filter: Filter
    )
}