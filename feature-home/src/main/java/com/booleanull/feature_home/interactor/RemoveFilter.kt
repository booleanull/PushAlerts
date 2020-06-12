package com.booleanull.feature_home.interactor

import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.functional.Either
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.Filter
import com.booleanull.feature_home.data.toFilterDTO

class RemoveFilter(private val alarmRepository: AlarmGateway) :
    BaseUseCase<Any, RemoveFilter.Params>() {

    override suspend fun run(params: Params?): Any {
        checkNotNull(params)
        alarmRepository.removeFilter(params.filter.toFilterDTO())
        return 0
    }

    data class Params(
        val filter: Filter
    )
}