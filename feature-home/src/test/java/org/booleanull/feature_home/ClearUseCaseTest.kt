package org.booleanull.feature_home

import kotlinx.coroutines.runBlocking
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.feature_home.interactor.ClearUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ClearUseCaseTest {

    private val alarmRepository = mock<AlarmRepository>()
    private val useCase = ClearUseCase(alarmRepository)

    @Test
    fun incrementCountAlarm(): Unit = runBlocking {
        useCase.run(null)
        Mockito.verify(alarmRepository).clear()
    }
}