package org.booleanull.feature_home

import com.booleanull.mock_factory.AlarmTestFactory
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.booleanull.core.functional.Task
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.feature_home.interactor.SearchAlarmUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SearchAlarmUseCaseTest {

    companion object {
        private const val SOME_PACKAGE_NAME = "some_package_name"
    }

    private val alarmRepository = mock<AlarmRepository>()
    private val useCase = SearchAlarmUseCase(alarmRepository)

    @Before
    fun setup(): Unit = runBlocking {
        Mockito.`when`(alarmRepository.searchAlarm(AlarmTestFactory.PACKAGE_NAME)).thenReturn(
            Task.Success(
                AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER
            )
        )
        Mockito.`when`(alarmRepository.searchAlarm(SOME_PACKAGE_NAME)).thenReturn(
            Task.Failure(
                Exception()
            )
        )
    }

    @Test
    fun searchSuccessAlarm() = runBlocking {
        val actual = useCase.run(SearchAlarmUseCase.Params(AlarmTestFactory.PACKAGE_NAME))
        val expected = Task.Success(AlarmTestFactory.EXAMPLE_ALARM_WITH_FILTER)

        assertEquals(actual, expected)
    }

    @Test
    fun searchFailureAlarm() = runBlocking {
        val actual = useCase.run(SearchAlarmUseCase.Params(SOME_PACKAGE_NAME))
        assertTrue(actual is Task.Failure)
    }

    @Test(expected = IllegalStateException::class)
    fun searchNullAlarm(): Unit = runBlocking {
        useCase.run(null)
    }
}