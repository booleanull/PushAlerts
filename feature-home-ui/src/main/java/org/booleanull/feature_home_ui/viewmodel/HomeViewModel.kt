package org.booleanull.feature_home_ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.booleanull.core.entity.Application
import org.booleanull.core.facade.SettingsFacade
import org.booleanull.core_ui.base.BaseViewModel
import org.booleanull.feature_home.interactor.GetApplicationListUseCase
import org.booleanull.feature_home.interactor.SearchApplicationListUseCase

internal class HomeViewModel(
    private val sharedViewModel: HomeSharedViewModel,
    private val getApplicationListUseCase: GetApplicationListUseCase,
    private val searchApplicationListUseCase: SearchApplicationListUseCase,
    private val settingsFacade: SettingsFacade
) : BaseViewModel(getApplicationListUseCase, searchApplicationListUseCase) {

    private val applicationListInternal = MutableLiveData<List<Application>>()
    val applicationList: LiveData<List<Application>>
        get() = applicationListInternal

    private val loadingInternal = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = loadingInternal

    private val applicationNotFoundInternal = MutableLiveData<Boolean>()
    val applicationNotFound: LiveData<Boolean>
        get() = applicationNotFoundInternal

    private val disabledAlarmInternal = MutableLiveData<Boolean>()
    val disabledAlarm: LiveData<Boolean>
        get() = disabledAlarmInternal

    val update: LiveData<Unit>
        get() = sharedViewModel.update

    var searchQuery = ""

    fun loadApplications(isUpdated: Boolean = false) {
        Log.d("LoadApplication", isUpdated.toString())
        searchQuery = ""
        applicationNotFoundInternal.value = false
        if (!isUpdated) {
            loadingInternal.value = true
        }
        getApplicationListUseCase.invoke(
            params = GetApplicationListUseCase.Params(
                GetApplicationListUseCase.SortType(GetApplicationListUseCase.SortType.SORT_FAVORITE)
            ), onResult = {
                loadingInternal.value = false
                applicationListInternal.value = it
            })
    }

    fun searchApplication(query: String) {
        searchApplicationListUseCase.dismiss()
        searchQuery = query
        loadingInternal.value = true
        applicationNotFoundInternal.value = false
        searchApplicationListUseCase.invoke(
            params = SearchApplicationListUseCase.Params(
                query,
                SearchApplicationListUseCase.SortType(GetApplicationListUseCase.SortType.SORT_FAVORITE)
            ),
            onResult = { task ->
                loadingInternal.value = false
                task.fold({ list ->
                    applicationListInternal.value = list
                }, {
                    applicationListInternal.value = emptyList()
                    applicationNotFoundInternal.value = true
                })
            })
    }

    fun loadAlarmStatus() {
        disabledAlarmInternal.value = settingsFacade.getAlarm()
    }
}