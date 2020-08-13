package com.booleanull.core.interactor

import android.content.Context
import com.booleanull.core.entity.Application
import com.booleanull.core.functional.Task
import com.booleanull.core.repository.ApplicationRepository

class GetApplicationUseCase(private val applicationRepository: ApplicationRepository) :
    BaseUseCase<Task<Exception, Application>, GetApplicationUseCase.Params>() {

    override suspend fun run(params: Params?): Task<Exception, Application> {
        checkNotNull(params)
        return applicationRepository.getApplication(params.context, params.packageName)
    }

    data class Params(
        val context: Context,
        val packageName: String
    )
}