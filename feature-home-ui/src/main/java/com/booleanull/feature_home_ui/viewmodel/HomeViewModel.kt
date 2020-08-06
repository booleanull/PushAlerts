package com.booleanull.feature_home_ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.booleanull.core_ui.base.BaseViewModel
import com.booleanull.feature_home.data.Application
import com.booleanull.feature_home.interactor.GetApplicationListUseCase
import com.booleanull.feature_home.interactor.SearchApplicationList

class HomeViewModel(
    private val context: Context,
    private val getApplicationListUseCase: GetApplicationListUseCase,
    private val searchApplicationList: SearchApplicationList
) : BaseViewModel(getApplicationListUseCase, searchApplicationList) {

    private val applicationListInternal = MutableLiveData<List<Application>>()
    val applicationList: LiveData<List<Application>>
        get() = applicationListInternal

    private val loadingInternal = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = loadingInternal

    private val applicationNotFoundInternal = MutableLiveData<Boolean>()
    val applicationNotFound: LiveData<Boolean>
        get() = applicationNotFoundInternal

    var isSearch = false

    fun loadApplications() {
        isSearch = false
        loadingInternal.value = true
        applicationNotFoundInternal.value = false
        getApplicationListUseCase.invoke(
            params = GetApplicationListUseCase.Params(
                context,
                GetApplicationListUseCase.SortType(GetApplicationListUseCase.SortType.SORT_NAME)
            ), onResult = {
                loadingInternal.value = false
                applicationListInternal.value = it
            })
    }

    fun searchApplication(query: String) {
        isSearch = true
        loadingInternal.value = true
        applicationNotFoundInternal.value = false
        searchApplicationList.invoke(
            params = SearchApplicationList.Params(
                context,
                query,
                SearchApplicationList.SortType(GetApplicationListUseCase.SortType.SORT_NAME)
            ),
            onResult = {
                loadingInternal.value = false
                it.fold({
                    applicationListInternal.value = it
                }, {
                    applicationListInternal.value = emptyList()
                    applicationNotFoundInternal.value = true
                })
            })
    }
}