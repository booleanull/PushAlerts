package org.booleanull.feature_home_ui.viewmodel

import androidx.lifecycle.LiveData
import org.booleanull.core_ui.base.BaseViewModel
import org.booleanull.core_ui.helper.SingleLiveEvent
import org.booleanull.feature_home.interactor.ClearUseCase

internal class SettingsViewModel(
    private val sharedViewModel: HomeSharedViewModel,
    private val clearUseCase: ClearUseCase
) : BaseViewModel(clearUseCase) {

    var updateDisableStatus: Boolean = false

    private val completeInternal = SingleLiveEvent<Unit>()
    val complete: LiveData<Unit> = completeInternal

    fun clear() {
        clearUseCase.invoke(onResult = {
            completeInternal.call()
        })
    }

    fun update() {
        sharedViewModel.update()
    }
}