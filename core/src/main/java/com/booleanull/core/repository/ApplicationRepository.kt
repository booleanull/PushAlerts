package com.booleanull.core.repository

import android.content.Context
import com.booleanull.core.entity.Application
import com.booleanull.core.functional.Task

interface ApplicationRepository {

    suspend fun getApplicationList(context: Context): List<Application>

    suspend fun searchApplicationList(
        context: Context,
        query: String
    ): Task<Exception, List<Application>>

    suspend fun getApplication(context: Context, packageName: String): Task<Exception, Application>
}