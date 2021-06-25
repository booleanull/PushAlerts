package org.booleanull.feature_home_ui

import org.booleanull.feature_home.interactor.*
import org.booleanull.feature_home_ui.viewmodel.HomeDetailsViewModel
import org.booleanull.feature_home_ui.viewmodel.HomeViewModel
import org.booleanull.feature_home_ui.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    factory { GetApplicationListUseCase(get()) }
    factory { SearchApplicationList(get()) }
    factory { SearchAlarmUseCase(get()) }
    factory { InsertAlarmUseCase(get()) }
    factory { InsertFilterUseCase(get()) }
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