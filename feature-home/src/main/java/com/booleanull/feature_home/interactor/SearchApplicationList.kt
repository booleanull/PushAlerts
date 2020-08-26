package com.booleanull.feature_home.interactor

import com.booleanull.core.entity.Application
import com.booleanull.core.functional.Task
import com.booleanull.core.functional.map
import com.booleanull.core.interactor.CoroutineUseCase
import com.booleanull.core.repository.ApplicationRepository

class SearchApplicationList(private val applicationRepository: ApplicationRepository) :
    CoroutineUseCase<Task<Exception, List<Application>>, SearchApplicationList.Params>() {

    override suspend fun run(params: Params?): Task<Exception, List<Application>> {
        checkNotNull(params)
        val applications = applicationRepository.searchApplicationList(params.query)
        return when (params.sortType.value) {
            GetApplicationListUseCase.SortType.SORT_NAME -> {
                applications.map { it.sortedBy { it.name } }
            }
            GetApplicationListUseCase.SortType.SORT_PACKAGE -> {
                applications.map { it.sortedBy { it.packageName } }
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
        }
    }
}