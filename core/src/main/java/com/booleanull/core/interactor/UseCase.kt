package com.booleanull.core.interactor

import kotlinx.coroutines.CoroutineScope

interface UseCase {

    fun join(coroutineScope: CoroutineScope)
}