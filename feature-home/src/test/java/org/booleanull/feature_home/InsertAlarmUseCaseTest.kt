package org.booleanull.feature_home

import com.booleanull.mock_factory.AlarmTestFactory
import kotlinx.coroutines.runBlocking
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.feature_home.interactor.InsertAlarmUseCase
import org.booleanull.feature_home.interactor.InsertFilterUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class InsertAlarmUseCaseTest {

    private val alarmRepository = mock<AlarmRepository>()
    private val useCase = InsertAlarmUseCase(alarmRepository)

    @Test
    fun insertAlarm(): Unit = runBlocking {
        useCase.run(InsertAlarmUseCase.Params(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER))
        Mockito.verify(alarmRepository).insertAlarm(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)
    }

    @Test(expected = IllegalStateException::class)
    fun insertNullAlarm(): Unit = runBlocking {
        useCase.run(null)
    }
}