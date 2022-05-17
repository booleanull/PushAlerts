package org.booleanull.feature_home

import com.booleanull.mock_factory.AlarmTestFactory
import kotlinx.coroutines.runBlocking
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.feature_home.interactor.IncrementCountAlarmUseCase
import org.booleanull.feature_home.interactor.InsertAlarmUseCase
import org.booleanull.feature_home.interactor.InsertFilterUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class IncrementCountAlarmUseCaseTest {

    private val alarmRepository = mock<AlarmRepository>()
    private val useCase = IncrementCountAlarmUseCase(alarmRepository)

    @Test
    fun incrementCountAlarm(): Unit = runBlocking {
        useCase.run(IncrementCountAlarmUseCase.Params(AlarmTestFactory.PACKAGE_NAME))
        Mockito.verify(alarmRepository).incrementCountAlarm(AlarmTestFactory.PACKAGE_NAME)
    }

    @Test(expected = IllegalStateException::class)
    fun incrementCountNullAlarm(): Unit = runBlocking {
        useCase.run(null)
    }
}