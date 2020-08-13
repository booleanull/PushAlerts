package com.booleanull.feature_home.interactor

import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.core.repository.AlarmRepository

class ClearUseCase(private val alarmRepository: AlarmRepository) : BaseUseCase<Unit, Unit>() {

    override suspend fun run(params: Unit?) {
        alarmRepository.clear()
    }
}