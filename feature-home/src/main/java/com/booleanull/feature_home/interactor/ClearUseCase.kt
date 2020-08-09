package com.booleanull.feature_home.interactor

import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.interactor.BaseUseCase

class ClearUseCase(private val alarmRepository: AlarmGateway) : BaseUseCase<Unit, Unit>() {

    override suspend fun run(params: Unit?) {
        alarmRepository.clear()
    }
}