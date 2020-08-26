package org.booleanull.core.repository

import org.booleanull.core.entity.Application
import org.booleanull.core.functional.Task

interface ApplicationRepository {

    suspend fun getApplicationList(): List<Application>

    suspend fun searchApplicationList(query: String): Task<Exception, List<Application>>

    suspend fun getApplication(packageName: String): Task<Exception, Application>
}