package com.booleanull.core.interactor

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<out Type, in Params> : UseCase, KoinComponent
        where Type : Any {

    private var scope: CoroutineScope? = null

    abstract suspend fun run(params: Params?): Type

    override fun join(coroutineScope: CoroutineScope) {
        scope = coroutineScope
    }

    operator fun invoke(
        params: Params? = null,
        onResult: (Type) -> Unit = {}
    ) {
        val defaultDispatcher: CoroutineContext = Dispatchers.Default
        val mainDispatcher: CoroutineContext = Dispatchers.Main

        scope?.async(defaultDispatcher) { run(params) }?.let { job ->
            scope?.launch(mainDispatcher) { onResult(job.await()) }
        }
    }

}