package org.booleanull.feature_home.interactor

import kotlinx.coroutines.delay
import org.booleanull.core.entity.Application
import org.booleanull.core.functional.Task
import org.booleanull.core.functional.map
import org.booleanull.core.interactor.CoroutineUseCase
import org.booleanull.core.repository.ApplicationRepository

class SearchApplicationListUseCase(private val applicationRepository: ApplicationRepository) :
    CoroutineUseCase<Task<Exception, List<Application>>, SearchApplicationListUseCase.Params>() {

    override suspend fun run(params: Params?): Task<Exception, List<Application>> {
        checkNotNull(params)
        delay(DELAY)
        val applications = applicationRepository.searchApplicationList(params.query)
        return when (params.sortType.value) {
            SortType.SORT_NAME -> {
                applications.map { it.sortedBy { application -> application.name } }
            }
            SortType.SORT_PACKAGE -> {
                applications.map { it.sortedBy { application -> application.packageName } }
            }
            SortType.SORT_FAVORITE -> {
                applications.map {
                    it.sortedByDescending { application ->
                        application.isFavorite
                    }
                }
            }
            else -> {
                applications
            }
        }
    }

    data class Params(
        val query: String,
        val sortType: SortType = SortType(SortType.SORT_NONE)
    )

    data class SortType(val value: Int) {
        companion object {
            const val SORT_NONE = 0
            const val SORT_NAME = 1
            const val SORT_PACKAGE = 2
            const val SORT_FAVORITE = 3
        }
    }

    companion object {
        private const val DELAY = 300L
    }
}