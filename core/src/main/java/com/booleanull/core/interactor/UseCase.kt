package com.booleanull.core.interactor

import kotlinx.coroutines.CoroutineScope

interface UseCase {

    fun setScope(coroutineScope: CoroutineScope)
}