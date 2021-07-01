package org.booleanull.core_ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.booleanull.core.interactor.UseCase

abstract class BaseViewModel(vararg useCases: UseCase) : ViewModel() {

    init {
        useCases.forEach { useCase ->
            useCase.join(viewModelScope)
        }
    }
}