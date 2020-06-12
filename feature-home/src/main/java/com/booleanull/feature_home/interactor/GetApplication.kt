package com.booleanull.feature_home.interactor

import android.content.Context
import com.booleanull.core.gateway.ApplicationGateway
import com.booleanull.core.functional.Either
import com.booleanull.core.functional.map
import com.booleanull.core.interactor.BaseUseCase
import com.booleanull.feature_home.data.Application
import com.booleanull.feature_home.data.toApplication

class GetApplication(private val applicationRepository: ApplicationGateway): BaseUseCase<Either<Exception, Application>, GetApplication.Params>() {

    override suspend fun run(params: Params?): Either<Exception, Application> {
        checkNotNull(params)
        return applicationRepository.getApplication(params.context, params.packageName).map { it.toApplication() }
    }

    data class Params(
        val context: Context,
        val packageName: String
    )
}