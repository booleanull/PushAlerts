package org.booleanull.feature_home_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.booleanull.core.entity.Alarm
import org.booleanull.core.entity.AlarmWithFilter
import org.booleanull.core.entity.Application
import org.booleanull.core.entity.Filter
import org.booleanull.core.interactor.GetApplicationUseCase
import org.booleanull.core_ui.base.BaseViewModel
import org.booleanull.core_ui.helper.SingleLiveEvent
import org.booleanull.feature_home.interactor.InsertAlarmUseCase
import org.booleanull.feature_home.interactor.InsertFilterUseCase
import org.booleanull.feature_home.interactor.RemoveFilterUseCase
import org.booleanull.feature_home.interactor.SearchAlarmUseCase

class HomeDetailsViewModel(
    private val packageName: String,
    private val getApplicationUseCase: GetApplicationUseCase,
    private val searchAlarmUseCase: SearchAlarmUseCase,
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val insertFilterUseCase: InsertFilterUseCase,
    private val removeFilterUseCase: RemoveFilterUseCase
) : BaseViewModel(
    getApplicationUseCase,
    searchAlarmUseCase,
    insertAlarmUseCase,
    insertFilterUseCase,
    removeFilterUseCase
) {

    private val applicationInternal = MutableLiveData<Application>()
    val application: LiveData<Application>
        get() = applicationInternal

    private val alarmInternal = MutableLiveData(
        AlarmWithFilter(
            Alarm(
                packageName,
                hasAlarm = false,
                hasFilter = false
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
        getApplicationUseCase.invoke(
            params = GetApplicationUseCase.Params(packageName),
            onResult = {
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
        alarmInternal.value = AlarmWithFilter(
            Alarm(packageName, status, alarmInternal.value!!.alarm.hasFilter),
            alarmInternal.value!!.filters
        )
        insertAlarmUseCase.invoke(
            params = InsertAlarmUseCase.Params(
                alarmInternal.value!!
            )
        )
    }

    fun setFilter(status: Boolean) {
        alarmInternal.value = AlarmWithFilter(
            Alarm(packageName, alarmInternal.value!!.alarm.hasAlarm, status),
            alarmInternal.value!!.filters
        )
        insertAlarmUseCase.invoke(
            params = InsertAlarmUseCase.Params(
                alarmInternal.value!!
            )
        )
    }

    fun addFilter(filter: String) {
        val alarm = alarmInternal.value!!
        val newFilter = Filter(packageName, filter)
        val filters = alarm.filters.toMutableSet()
        filters.add(newFilter)
        alarmInternal.value =
            AlarmWithFilter(
                Alarm(packageName, alarm.alarm.hasAlarm, alarm.alarm.hasFilter),
                filters
            )
        insertFilterUseCase.invoke(params = InsertFilterUseCase.Params(newFilter))
    }

    fun removeFilter(filter: String) {
        val alarm = alarmInternal.value!!
        val removeFilter = Filter(packageName, filter)
        val filters = alarm.filters.toMutableSet()
        filters.remove(filters.find { it.filter == filter })
        alarmInternal.value =
            AlarmWithFilter(
                Alarm(packageName, alarm.alarm.hasAlarm, alarm.alarm.hasFilter),
                filters
            )
        removeFilterUseCase.invoke(params = RemoveFilterUseCase.Params(removeFilter))
    }
}