package com.booleanull.feature_home_ui.di

import com.booleanull.feature_home.interactor.*
import com.booleanull.feature_home_ui.viewmodel.HomeDetailsViewModel
import com.booleanull.feature_home_ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    factory { GetApplicationList(get()) }
    factory { SearchApplicationList(get()) }
    factory { GetApplication(get()) }
    factory { SearchAlarm(get()) }
    factory { InsertAlarm(get()) }
    factory { RemoveFilter(get()) }

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