package org.booleanull.core.interactor

import com.booleanull.mock_factory.AlarmTestFactory
import com.booleanull.mock_factory.ApplicationTestFactory
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.booleanull.core.functional.Task
import org.booleanull.core.repository.ApplicationRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetApplicationUseCaseTest {

    companion object {
        private const val SOME_PACKAGE_NAME = "some_package_name"
    }

    private val applicationRepository = mock<ApplicationRepository>()
    private val useCase = GetApplicationUseCase(applicationRepository)

    @Before
    fun setup(): Unit = runBlocking {
        Mockito.`when`(applicationRepository.getApplication(AlarmTestFactory.PACKAGE_NAME))
            .thenReturn(
                Task.Success(
                    ApplicationTestFactory.EXAMPLE_APPLICATION
                )
            )
        Mockito.`when`(applicationRepository.getApplication(SOME_PACKAGE_NAME)).thenReturn(
            Task.Failure(
                Exception()
            )
        )
    }

    @Test
    fun getSuccessApplication() = runBlocking {
        val actual = useCase.run(GetApplicationUseCase.Params(AlarmTestFactory.PACKAGE_NAME))
        val expected = Task.Success(ApplicationTestFactory.EXAMPLE_APPLICATION)

        assertEquals(actual, expected)
    }

    @Test
    fun getFailureApplication() = runBlocking {
        val actual = useCase.run(GetApplicationUseCase.Params(SOME_PACKAGE_NAME))
        assertTrue(actual is Task.Failure)
    }

    @Test(expected = IllegalStateException::class)
    fun getNullApplication(): Unit = runBlocking {
        useCase.run(null)
    }
}