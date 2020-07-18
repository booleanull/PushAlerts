package com.booleanull.feature_home.interactor

import android.content.Context
import com.booleanull.core.functional.Task
import com.booleanull.core.functional.map
import com.booleanull.core.gateway.ApplicationGateway
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.Application
import com.booleanull.feature_home.data.toApplication

class SearchApplicationList(private val applicationRepository: ApplicationGateway) :
    BaseUseCase<Task<Exception, List<Application>>, SearchApplicationList.Params>() {

    override suspend fun run(params: Params?): Task<Exception, List<Application>> {
        checkNotNull(params)
        val applications = applicationRepository.searchApplicationList(params.context, params.query)
            .map { it.map { it.toApplication() } }
        return when (params.sortType.value) {
            GetApplicationList.SortType.SORT_NAME -> {
                applications.map { it.sortedBy { it.name } }
            }
            GetApplicationList.SortType.SORT_PACKAGE -> {
                applications.map { it.sortedBy { it.packageName } }
            }
            else -> {
                applications
            }
        }
    }

    data class Params(
        val context: Context,
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