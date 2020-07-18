package com.booleanull.core_ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booleanull.core.interactor.UseCase

open class BaseViewModel(vararg useCases: UseCase) : ViewModel() {

    init {
        useCases.forEach { useCase ->
            useCase.join(viewModelScope)
        }
    }
}