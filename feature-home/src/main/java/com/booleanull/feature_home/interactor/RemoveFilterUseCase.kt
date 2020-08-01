package com.booleanull.feature_home.interactor

import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.Filter
import com.booleanull.feature_home.data.toFilterDTO

class RemoveFilterUseCase(private val alarmRepository: AlarmGateway) :
    BaseUseCase<Unit, RemoveFilterUseCase.Params>() {

    override suspend fun run(params: Params?) {
        checkNotNull(params)
        alarmRepository.removeFilter(params.filter.toFilterDTO())
    }

    data class Params(
        val filter: Filter
    )
}