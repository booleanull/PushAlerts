/*
package org.booleanull.feature_alarm_ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.booleanull.mock_factory.AlarmTestFactory
import com.booleanull.mock_factory.ApplicationTestFactory
import com.booleanull.mock_factory.getOrAwaitValueTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.booleanull.core.functional.Task
import org.booleanull.core.interactor.GetApplicationUseCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class AlarmViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var alarmViewModel: AlarmViewModel
    private val getApplicationUseCase = mock<GetApplicationUseCase>()

    @Before
    fun setup() = runBlocking {
        Dispatchers.setMain(dispatcher)
        Mockito.`when`(getApplicationUseCase.run(GetApplicationUseCase.Params(AlarmTestFactory.PACKAGE_NAME)))
            .thenReturn(
                Task.Success(ApplicationTestFactory.EXAMPLE_APPLICATION)
            )
        alarmViewModel = AlarmViewModel(AlarmTestFactory.PACKAGE_NAME, getApplicationUseCase)
    }

    @After
    fun clear() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadApplication() = runTest {
        alarmViewModel.loadApplication()
        val actual = alarmViewModel.application.getOrAwaitValueTest()
        val expected = ApplicationTestFactory.EXAMPLE_APPLICATION
        assertEquals(actual, expected)
    }
}*/
