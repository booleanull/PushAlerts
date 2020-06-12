package com.booleanull.feature_home_ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.booleanull.core_ui.base.BaseViewModel
import com.booleanull.core_ui.SingleLiveEvent
import com.booleanull.feature_home.data.Alarm
import com.booleanull.feature_home.data.AlarmWithFilter
import com.booleanull.feature_home.data.Application
import com.booleanull.feature_home.data.Filter
import com.booleanull.feature_home.interactor.*

class HomeDetailsViewModel(
    private val packageName: String,
    private val context: Context,
    private val getApplication: GetApplication,
    private val searchAlarm: SearchAlarm,
    private val insertAlarm: InsertAlarm,
    private val removeFilter: RemoveFilter
) : BaseViewModel(
    getApplication,
    searchAlarm,
    insertAlarm,
    removeFilter
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
            mutableListOf()
        )
    )
    val alarm: LiveData<AlarmWithFilter>
        get() = alarmInternal

    private val errorNotFoundInternal = SingleLiveEvent<Nothing>()
    val errorNotFound: LiveData<Nothing>
        get() = errorNotFoundInternal

    fun loadApplication() {
        getApplication.invoke(params = GetApplication.Params(context, packageName), onResult = {
            it.fold({
                applicationInternal.value = it
            }, {
                errorNotFoundInternal.call()
            })
        })
    }

    fun searchApplicationAlarm() {
        searchAlarm.invoke(SearchAlarm.Params(packageName), onResult = {
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
        alarmInternal.value = alarmInternal.value!!.apply { filters.add(0,
            Filter(packageName, filter)
        ) }
        insertAlarm()
    }

    fun removeFilter(filter: String) {
        val item = alarmInternal.value!!.filters.firstOrNull { it.filter == filter } ?: return
        alarmInternal.value = alarmInternal.value!!.apply { filters.remove(item) }
        removeFilter.invoke(params = RemoveFilter.Params(item))
    }

    private fun insertAlarm() {
        insertAlarm.invoke(params = InsertAlarm.Params(alarmInternal.value!!))
    }
}