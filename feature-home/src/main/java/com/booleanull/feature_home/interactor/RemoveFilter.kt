package com.booleanull.feature_home.interactor

import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.Filter
import com.booleanull.feature_home.data.toFilterDTO

class RemoveFilter(private val alarmRepository: AlarmGateway) :
    BaseUseCase<Unit, RemoveFilter.Params>() {

    override suspend fun run(params: Params?) {
        checkNotNull(params)
        alarmRepository.removeFilter(params.filter.toFilterDTO())
    }

    data class Params(
        val filter: Filter
    )
}