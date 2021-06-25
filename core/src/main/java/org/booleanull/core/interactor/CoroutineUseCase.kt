package org.booleanull.core.interactor

import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class CoroutineUseCase<out Type, in Params> : UseCase, KoinComponent
        where Type : Any {

    private var coroutineJob: Deferred<Type>? = null
    private var scope: CoroutineScope? = null

    abstract suspend fun run(params: Params?): Type

    override fun join(coroutineScope: CoroutineScope) {
        scope = coroutineScope
    }

    operator fun invoke(
        params: Params? = null,
        onResult: (Type) -> Unit = {},
        dispatcher: CoroutineContext = Dispatchers.Default
    ) {
        val mainDispatcher: CoroutineContext = Dispatchers.Main

        scope?.async(dispatcher) { run(params) }?.let { job ->
            coroutineJob = job
            scope?.launch(mainDispatcher) { onResult(job.await()) }
        }
    }

    fun dismiss() {
        coroutineJob?.cancel()
    }
}