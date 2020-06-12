package com.booleanull.core.gateway

import android.content.Context
import com.booleanull.core.dto.ApplicationDTO
import com.booleanull.core.functional.Either
import java.lang.Exception

interface ApplicationGateway {

    suspend fun getApplicationList(context: Context): List<ApplicationDTO>

    suspend fun searchApplicationList(context: Context, query: String): Either<Exception, List<ApplicationDTO>>

    suspend fun getApplication(context: Context, packageName: String): Either<Exception, ApplicationDTO>
}