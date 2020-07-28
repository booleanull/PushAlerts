package com.booleanull.feature_home_ui

import com.booleanull.feature_home.interactor.*
import com.booleanull.feature_home_ui.viewmodel.HomeDetailsViewModel
import com.booleanull.feature_home_ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    factory { GetApplicationListUseCase(get()) }
    factory { SearchApplicationList(get()) }
    factory { GetApplicationUseCase(get()) }
    factory { SearchAlarmUseCase(get()) }
    factory { InsertAlarmUseCase(get()) }
    factory { RemoveFilterUseCase(get()) }

    viewModel {
        HomeViewModel(
            get(),
            get(),
            get()
        )
    }
    viewModel { (packageName: String) ->
        HomeDetailsViewModel(
            packageName,
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}