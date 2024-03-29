package org.booleanull.feature_home.interactor

import org.booleanull.core.entity.Application
import org.booleanull.core.interactor.CoroutineUseCase
import org.booleanull.core.repository.ApplicationRepository

class GetApplicationListUseCase(
    private val applicationRepository: ApplicationRepository
) :
    CoroutineUseCase<List<Application>, GetApplicationListUseCase.Params>() {

    override suspend fun run(params: Params?): List<Application> {
        checkNotNull(params)
        val applications = applicationRepository.getApplicationList()
        return when (params.sortType.value) {
            SortType.SORT_NAME -> {
                applications.sortedBy { it.name }
            }
            SortType.SORT_PACKAGE -> {
                applications.sortedBy { it.packageName }
            }
            SortType.SORT_FAVORITE -> {
                applications.sortedByDescending { application ->
                    application.isFavorite
                }
            }
            else -> {
                applications
            }
        }
    }

    data class Params(
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
}