package com.booleanull.feature_home_ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.booleanull.core_ui.base.BaseViewModel
import com.booleanull.feature_home.data.Application
import com.booleanull.feature_home.interactor.GetApplicationList
import com.booleanull.feature_home.interactor.SearchApplicationList

class HomeViewModel(
    private val context: Context,
    private val getApplicationList: GetApplicationList,
    private val searchApplicationList: SearchApplicationList
) : BaseViewModel(getApplicationList, searchApplicationList) {

    private val applicationListInternal = MutableLiveData<List<Application>>()
    val applicationList: LiveData<List<Application>>
        get() = applicationListInternal

    private val loadingInternal = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = loadingInternal

    private val applicationNotFoundInternal = MutableLiveData<Boolean>()
    val applicationNotFound: LiveData<Boolean>
        get() = applicationNotFoundInternal

    private val searchVisibleInternal = MutableLiveData<Boolean>(false)
    val searchVisible: LiveData<Boolean>
        get() = searchVisibleInternal

    private val searchLoadingInternal = MutableLiveData<Boolean>()
    val searchLoading: LiveData<Boolean>
        get() = searchLoadingInternal

    var isSearch = false
        private set

    fun loadApplications() {
        isSearch = false
        loadingInternal.value = true
        applicationNotFoundInternal.value = false
        getApplicationList.invoke(
            params = GetApplicationList.Params(
                context,
                GetApplicationList.SortType(GetApplicationList.SortType.SORT_NAME)
            ), onResult = {
                loadingInternal.value = false
                applicationListInternal.value = it
            })
    }

    fun searchApplication(query: String) {
        isSearch = true
        searchLoadingInternal.value = true
        applicationNotFoundInternal.value = false
        searchApplicationList.invoke(
            params = SearchApplicationList.Params(
                context,
                query,
                SearchApplicationList.SortType(GetApplicationList.SortType.SORT_NAME)
            ),
            onResult = {
                searchLoadingInternal.value = false
                it.fold({
                    applicationListInternal.value = it
                }, {
                    applicationListInternal.value = emptyList()
                    applicationNotFoundInternal.value = true
                })
            })
    }

    fun changeSearchVisible(status: Boolean? = null) {
        if (status == null) {
            searchVisibleInternal.value = !searchVisibleInternal.value!!
        } else {
            searchVisibleInternal.value = status
        }
    }
}