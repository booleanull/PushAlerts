package org.booleanull.feature_home

import com.booleanull.mock_factory.ApplicationTestFactory
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.booleanull.core.functional.Task
import org.booleanull.core.repository.ApplicationRepository
import org.booleanull.feature_home.interactor.GetApplicationListUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetApplicationListUseCaseTest {

    private val applicationRepository = mock<ApplicationRepository>()
    private val useCase = GetApplicationListUseCase(applicationRepository)

    @Before
    fun setup(): Unit = runBlocking {
        Mockito.`when`(applicationRepository.getApplicationList())
            .thenReturn(
                ApplicationTestFactory.getAlarms()
            )
    }

    @Test
    fun getApplications() = runBlocking {
        val actual = useCase.run(GetApplicationListUseCase.Params())
        val expected = ApplicationTestFactory.getAlarms()
        assertEquals(actual, expected)
    }

    @Test
    fun getSortByNameApplications() = runBlocking {
        val actual = useCase.run(
            GetApplicationListUseCase.Params(
                GetApplicationListUseCase.SortType(1)
            )
        )
        val expected = ApplicationTestFactory.getAlarms().sortedBy { it.name }
        assertEquals(actual, expected)
    }

    @Test
    fun getSortByPackageApplications() = runBlocking {
        val actual = useCase.run(
            GetApplicationListUseCase.Params(
                GetApplicationListUseCase.SortType(2)
            )
        )
        val expected = ApplicationTestFactory.getAlarms().sortedBy { it.packageName }
        assertEquals(actual, expected)
    }

    @Test
    fun getSortByFavoriteApplications() = runBlocking {
        val actual = useCase.run(
            GetApplicationListUseCase.Params(
                GetApplicationListUseCase.SortType(3)
            )
        )
        val expected = ApplicationTestFactory.getAlarms().sortedBy { it.isFavorite }
        assertEquals(actual, expected)
    }

    @Test(expected = IllegalStateException::class)
    fun getNullApplications(): Unit = runBlocking {
        useCase.run(null)
    }
}