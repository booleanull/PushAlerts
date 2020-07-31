package com.booleanull.feature_home.interactor

import android.content.Context
import com.booleanull.core.gateway.ApplicationGateway
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.Application
import com.booleanull.feature_home.data.toApplication

class GetApplicationListUseCase(private val applicationRepository: ApplicationGateway): BaseUseCase<List<Application>, GetApplicationListUseCase.Params>() {

    override suspend fun run(params: Params?): List<Application> {
        checkNotNull(params)
        val applications = applicationRepository.getApplicationList(params.context).map { it.toApplication() }
        return when(params.sortType.value) {
            SortType.SORT_NAME -> {
                applications.sortedBy { it.name }
            }
            SortType.SORT_PACKAGE -> {
                applications.sortedBy { it.packageName }
            }
            else -> {
                applications
            }
        }
    }

    data class Params(
        val context: Context,
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