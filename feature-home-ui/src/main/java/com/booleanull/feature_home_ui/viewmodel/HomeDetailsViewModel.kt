package com.booleanull.feature_home_ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.booleanull.core.data.Application
import com.booleanull.core.interactor.GetApplicationUseCase
import com.booleanull.core_ui.base.BaseViewModel
import com.booleanull.core_ui.helper.SingleLiveEvent
import com.booleanull.feature_home.data.Alarm
import com.booleanull.feature_home.data.AlarmWithFilter
import com.booleanull.feature_home.data.Filter
import com.booleanull.feature_home.interactor.*

class HomeDetailsViewModel(
    private val packageName: String,
    private val context: Context,
    private val getApplicationUseCase: GetApplicationUseCase,
    private val searchAlarmUseCase: SearchAlarmUseCase,
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val removeFilterUseCase: RemoveFilterUseCase
) : BaseViewModel(
    getApplicationUseCase,
    searchAlarmUseCase,
    insertAlarmUseCase,
    removeFilterUseCase
) {

    private val applicationInternal = MutableLiveData<Application>()
    val application: LiveData<Application>
        get() = applicationInternal

    private val alarmInternal = MutableLiveData(
        AlarmWithFilter(
            Alarm(
                packageName,
                isAlarm = false,
                isFilter = false
            ),
            mutableSetOf()
        )
    )
    val alarm: LiveData<AlarmWithFilter>
        get() = alarmInternal

    private val errorNotFoundInternal =
        SingleLiveEvent<Nothing>()
    val errorNotFound: LiveData<Nothing>
        get() = errorNotFoundInternal

    fun loadApplication() {
        getApplicationUseCase.invoke(params = GetApplicationUseCase.Params(context, packageName), onResult = {
            it.fold({
                applicationInternal.value = it
            }, {
                errorNotFoundInternal.call()
            })
        })
    }

    fun searchApplicationAlarm() {
        searchAlarmUseCase.invoke(SearchAlarmUseCase.Params(packageName), onResult = {
            it.fold({
                alarmInternal.value = it
            })
        })
    }

    fun setAlarm(status: Boolean) {
        alarmInternal.value = alarmInternal.value!!.apply { alarm.isAlarm = status }
        insertAlarm()
    }

    fun setFilter(status: Boolean) {
        alarmInternal.value =
            alarmInternal.value!!.apply { alarm.isFilter = status }
        insertAlarm()
    }

    fun addFilter(filter: String) {
        alarmInternal.value = alarmInternal.value!!.apply { filters.add(
            Filter(packageName, filter)
        ) }
        insertAlarm()
    }

    fun removeFilter(filter: String) {
        val item = alarmInternal.value!!.filters.firstOrNull { it.filter == filter } ?: return
        alarmInternal.value = alarmInternal.value!!.apply { filters.remove(item) }
        removeFilterUseCase.invoke(params = RemoveFilterUseCase.Params(item))
    }

    private fun insertAlarm() {
        insertAlarmUseCase.invoke(params = InsertAlarmUseCase.Params(alarmInternal.value!!))
    }
}