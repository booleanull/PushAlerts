package org.booleanull.core.interactor

import org.booleanull.core.entity.Application
import org.booleanull.core.functional.Task
import org.booleanull.core.repository.ApplicationRepository

class GetApplicationUseCase(private val applicationRepository: ApplicationRepository) :
    CoroutineUseCase<Task<Exception, Application>, GetApplicationUseCase.Params>() {

    override suspend fun run(params: Params?): Task<Exception, Application> {
        checkNotNull(params)
        return applicationRepository.getApplication(params.packageName)
    }

    data class Params(
        val packageName: String
    )
}