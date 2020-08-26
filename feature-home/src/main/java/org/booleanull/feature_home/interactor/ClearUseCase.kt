package org.booleanull.feature_home.interactor

import org.booleanull.core.interactor.CoroutineUseCase
import org.booleanull.core.repository.AlarmRepository

class ClearUseCase(private val alarmRepository: AlarmRepository) : CoroutineUseCase<Unit, Unit>() {

    override suspend fun run(params: Unit?) {
        alarmRepository.clear()
    }
}