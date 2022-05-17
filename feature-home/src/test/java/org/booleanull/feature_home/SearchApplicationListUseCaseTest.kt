package org.booleanull.feature_home

import com.booleanull.mock_factory.AlarmTestFactory
import com.booleanull.mock_factory.ApplicationTestFactory
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.booleanull.core.functional.Task
import org.booleanull.core.repository.ApplicationRepository
import org.booleanull.feature_home.interactor.SearchApplicationListUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SearchApplicationListUseCaseTest {

    companion object {
        private const val SOME_PACKAGE_NAME = "some_package_name"
    }

    private val applicationRepository = mock<ApplicationRepository>()
    private val useCase = SearchApplicationListUseCase(applicationRepository)

    @Before
    fun setup(): Unit = runBlocking {
        Mockito.`when`(applicationRepository.searchApplicationList(AlarmTestFactory.PACKAGE_NAME))
            .thenReturn(
                Task.Success(
                    ApplicationTestFactory.getAlarms()
                )
            )
        Mockito.`when`(applicationRepository.searchApplicationList(SOME_PACKAGE_NAME)).thenReturn(
            Task.Failure(
                Exception()
            )
        )
    }

    @Test
    fun searchSuccessApplications() = runBlocking {
        val actual = useCase.run(SearchApplicationListUseCase.Params(AlarmTestFactory.PACKAGE_NAME))
        val expected = Task.Success(ApplicationTestFactory.getAlarms())
        assertEquals(actual, expected)
    }

    @Test
    fun searchSuccessSortByNameApplications() = runBlocking {
        val actual = useCase.run(
            SearchApplicationListUseCase.Params(
                AlarmTestFactory.PACKAGE_NAME,
                SearchApplicationListUseCase.SortType(1)
            )
        )
        val expected = Task.Success(ApplicationTestFactory.getAlarms().sortedBy { it.name })
        assertEquals(actual, expected)
    }

    @Test
    fun searchSuccessSortByPackageApplications() = runBlocking {
        val actual = useCase.run(
            SearchApplicationListUseCase.Params(
                AlarmTestFactory.PACKAGE_NAME,
                SearchApplicationListUseCase.SortType(2)
            )
        )
        val expected = Task.Success(ApplicationTestFactory.getAlarms().sortedBy { it.packageName })
        assertEquals(actual, expected)
    }

    @Test
    fun searchSuccessSortByFavoriteApplications() = runBlocking {
        val actual = useCase.run(
            SearchApplicationListUseCase.Params(
                AlarmTestFactory.PACKAGE_NAME,
                SearchApplicationListUseCase.SortType(3)
            )
        )
        val expected = Task.Success(ApplicationTestFactory.getAlarms().sortedBy { it.isFavorite })
        assertEquals(actual, expected)
    }

    @Test
    fun searchFailureApplications() = runBlocking {
        val actual = useCase.run(SearchApplicationListUseCase.Params(SOME_PACKAGE_NAME))
        assertTrue(actual is Task.Failure)
    }

    @Test(expected = IllegalStateException::class)
    fun searchNullApplications(): Unit = runBlocking {
        useCase.run(null)
    }
}