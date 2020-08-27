package org.booleanull.core_ui

import org.booleanull.core.interactor.GetApplicationUseCase
import org.koin.dsl.module

val coreModule = module {

    factory { GetApplicationUseCase(get()) }
}