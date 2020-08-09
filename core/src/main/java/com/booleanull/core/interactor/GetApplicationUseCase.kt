package com.booleanull.core.interactor

import android.content.Context
import com.booleanull.core.data.Application
import com.booleanull.core.data.toApplication
import com.booleanull.core.gateway.ApplicationGateway
import com.booleanull.core.functional.Task
import com.booleanull.core.functional.map

class GetApplicationUseCase(private val applicationRepository: ApplicationGateway): BaseUseCase<Task<Exception, Application>, GetApplicationUseCase.Params>() {

    override suspend fun run(params: Params?): Task<Exception, Application> {
        checkNotNull(params)
        return applicationRepository.getApplication(params.context, params.packageName).map { it.toApplication() }
    }

    data class Params(
        val context: Context,
        val packageName: String
    )
}