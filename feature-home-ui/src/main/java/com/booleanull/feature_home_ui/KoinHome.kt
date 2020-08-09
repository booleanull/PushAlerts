package com.booleanull.feature_home_ui

import com.booleanull.feature_home.interactor.*
import com.booleanull.feature_home_ui.viewmodel.HomeDetailsViewModel
import com.booleanull.feature_home_ui.viewmodel.HomeViewModel
import com.booleanull.feature_home_ui.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    factory { GetApplicationListUseCase(get()) }
    factory { SearchApplicationList(get()) }
    factory { SearchAlarmUseCase(get()) }
    factory { InsertAlarmUseCase(get()) }
    factory { RemoveFilterUseCase(get()) }
    factory { ClearUseCase(get()) }

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
    viewModel { SettingsViewModel(get()) }
}