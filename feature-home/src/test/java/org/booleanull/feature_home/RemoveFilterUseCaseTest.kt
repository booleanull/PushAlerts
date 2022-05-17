package org.booleanull.feature_home

import com.booleanull.mock_factory.AlarmTestFactory
import kotlinx.coroutines.runBlocking
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.feature_home.interactor.RemoveFilterUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class RemoveFilterUseCaseTest {

    private val alarmRepository = mock<AlarmRepository>()
    private val useCase = RemoveFilterUseCase(alarmRepository)

    @Test
    fun removeFilter(): Unit = runBlocking {
        useCase.run(RemoveFilterUseCase.Params(AlarmTestFactory.EXAMPLE_FILTER))
        Mockito.verify(alarmRepository).removeFilter(AlarmTestFactory.EXAMPLE_FILTER)
    }

    @Test(expected = IllegalStateException::class)
    fun removeNullFilter(): Unit = runBlocking {
        useCase.run(null)
    }
}