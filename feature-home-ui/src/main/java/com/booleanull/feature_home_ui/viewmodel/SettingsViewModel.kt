package com.booleanull.feature_home_ui.viewmodel

import androidx.lifecycle.LiveData
import com.booleanull.core_ui.base.BaseViewModel
import com.booleanull.core_ui.helper.SingleLiveEvent
import com.booleanull.feature_home.interactor.ClearUseCase

class SettingsViewModel(private val clearUseCase: ClearUseCase) : BaseViewModel(clearUseCase) {

    private val completeInternal = SingleLiveEvent<Unit>()
    val complete: LiveData<Unit> = completeInternal

    fun clear() {
        clearUseCase.invoke {
            completeInternal.call()
        }
    }
}