package com.booleanull.feature_alarm_ui

import com.booleanull.feature_alarm_ui.viewmodel.AlarmViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val alarmModule = module {

    viewModel { (packageName: String) -> AlarmViewModel(packageName, get()) }
}