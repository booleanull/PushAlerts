package com.booleanull.feature_alarm_ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.booleanull.core.data.Application
import com.booleanull.core.interactor.GetApplicationUseCase
import com.booleanull.core_ui.base.BaseViewModel
import com.booleanull.core_ui.helper.SingleLiveEvent

class AlarmViewModel(
    private val packageName: String,
    private val context: Context,
    private val getApplicationUseCase: GetApplicationUseCase
) : BaseViewModel(getApplicationUseCase) {

    private val applicationInternal = MutableLiveData<Application>()
    val application: LiveData<Application>
        get() = applicationInternal

    fun loadApplication() {
        getApplicationUseCase.invoke(params = GetApplicationUseCase.Params(context, packageName), onResult = {
            it.fold({
                applicationInternal.value = it
            }, {
                errorNotFoundInternal.call()
            })
        })
    }

    private val errorNotFoundInternal =
        SingleLiveEvent<Nothing>()
    val errorNotFound: LiveData<Nothing>
        get() = errorNotFoundInternal
}