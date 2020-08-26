package com.booleanull.core.repository

import com.booleanull.core.entity.Application
import com.booleanull.core.functional.Task

interface ApplicationRepository {

    suspend fun getApplicationList(): List<Application>

    suspend fun searchApplicationList(query: String): Task<Exception, List<Application>>

    suspend fun getApplication(packageName: String): Task<Exception, Application>
}