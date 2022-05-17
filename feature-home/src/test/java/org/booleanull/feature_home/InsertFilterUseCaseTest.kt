package org.booleanull.feature_home

import com.booleanull.mock_factory.AlarmTestFactory
import kotlinx.coroutines.runBlocking
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.feature_home.interactor.InsertFilterUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class InsertFilterUseCaseTest {

    private val alarmRepository = mock<AlarmRepository>()
    private val useCase = InsertFilterUseCase(alarmRepository)

    @Test
    fun insertFilter(): Unit = runBlocking {
        useCase.run(InsertFilterUseCase.Params(AlarmTestFactory.EXAMPLE_FILTER))
        Mockito.verify(alarmRepository).insertFilter(AlarmTestFactory.EXAMPLE_FILTER)
    }

    @Test(expected = IllegalStateException::class)
    fun insertNullFilter(): Unit = runBlocking {
        useCase.run(null)
    }
}