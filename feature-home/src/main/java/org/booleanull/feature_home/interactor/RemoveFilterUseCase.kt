package org.booleanull.feature_home.interactor

import org.booleanull.core.entity.Filter
import org.booleanull.core.interactor.CoroutineUseCase
import org.booleanull.core.repository.AlarmRepository

class RemoveFilterUseCase(private val alarmRepository: AlarmRepository) :
    CoroutineUseCase<Unit, RemoveFilterUseCase.Params>() {

    override suspend fun run(params: Params?) {
        checkNotNull(params)
        alarmRepository.removeFilter(params.filter)
    }

    data class Params(
        val filter: Filter
    )
}