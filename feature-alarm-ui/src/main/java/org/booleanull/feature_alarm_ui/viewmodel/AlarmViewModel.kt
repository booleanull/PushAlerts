package org.booleanull.feature_alarm_ui.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.booleanull.core.entity.Application
import org.booleanull.core.interactor.GetApplicationUseCase
import org.booleanull.core_ui.base.BaseViewModel
import org.booleanull.core_ui.helper.SingleLiveEvent

class AlarmViewModel(
    private val packageName: String,
    private val getApplicationUseCase: GetApplicationUseCase
) : BaseViewModel(getApplicationUseCase) {

    private val applicationInternal = MutableLiveData<Application>()
    val application: LiveData<Application>
        get() = applicationInternal

    private val errorNotFoundInternal = SingleLiveEvent<Nothing>()
    val errorNotFound: LiveData<Nothing>
        get() = errorNotFoundInternal

    private val finishInternal = SingleLiveEvent<Nothing>()
    val finish: LiveData<Nothing>
        get() = finishInternal

    private var timer: CountDownTimer? = null

    init {
        timer = object : CountDownTimer(30000, 1000) {

            override fun onFinish() {
                finishInternal.call()
            }

            override fun onTick(p0: Long) = Unit
        }.start()
    }

    fun loadApplication() {
        getApplicationUseCase.invoke(
            params = GetApplicationUseCase.Params(packageName),
            onResult = { task ->
                task.fold({ application ->
                    applicationInternal.value = application
                }, {
                    errorNotFoundInternal.call()
                })
            })
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}