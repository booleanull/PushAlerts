package org.booleanull.feature_home.interactor

import org.booleanull.core.entity.Filter
import org.booleanull.core.interactor.CoroutineUseCase
import org.booleanull.core.repository.AlarmRepository

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