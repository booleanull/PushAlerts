package com.booleanull.core_ui

import com.booleanull.core.interactor.GetApplicationUseCase
import org.koin.dsl.module

val coreModule = module {

    factory { GetApplicationUseCase(get()) }
}