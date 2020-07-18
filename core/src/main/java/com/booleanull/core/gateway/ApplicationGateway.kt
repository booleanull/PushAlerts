package com.booleanull.core.gateway

import android.content.Context
import com.booleanull.core.dto.ApplicationDTO
import com.booleanull.core.functional.Task
import java.lang.Exception

interface ApplicationGateway {

    suspend fun getApplicationList(context: Context): List<ApplicationDTO>

    suspend fun searchApplicationList(context: Context, query: String): Task<Exception, List<ApplicationDTO>>

    suspend fun getApplication(context: Context, packageName: String): Task<Exception, ApplicationDTO>
}