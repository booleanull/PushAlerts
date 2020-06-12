package com.booleanull.core.interactor

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<out Type, in Params> : UseCase, KoinComponent
        where Type : Any {

    private lateinit var scope: CoroutineScope

    override fun setScope(coroutineScope: CoroutineScope) {
        scope = coroutineScope
    }

    abstract suspend fun run(params: Params?): Type

    operator fun invoke(
        params: Params? = null,
        onResult: (Type) -> Unit = {}
    ) {
        val defaultDispatcher: CoroutineContext = Dispatchers.Default
        val mainDispatcher: CoroutineContext = Dispatchers.Main

        val job = scope.async(defaultDispatcher) { run(params) }
        scope.launch(mainDispatcher) { onResult(job.await()) }
    }
}