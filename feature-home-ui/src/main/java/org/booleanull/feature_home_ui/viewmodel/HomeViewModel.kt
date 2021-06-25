package org.booleanull.feature_home_ui.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.booleanull.core.entity.Application
import org.booleanull.core_ui.base.BaseViewModel
import org.booleanull.feature_home.interactor.GetApplicationListUseCase
import org.booleanull.feature_home.interactor.SearchApplicationList

class HomeViewModel(
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

    private var timer: CountDownTimer? = null

    var searchQuery = ""

    fun loadApplications(isUpdated: Boolean = false) {
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
        timer?.cancel()
        searchApplicationList.dismiss()
        searchQuery = query
        loadingInternal.value = true
        applicationNotFoundInternal.value = false
        timer = object : CountDownTimer(300L, 100L) {
            override fun onFinish() {
                searchApplicationList.invoke(
                    params = SearchApplicationList.Params(
                        query,
                        SearchApplicationList.SortType(GetApplicationListUseCase.SortType.SORT_NAME)
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

            override fun onTick(p0: Long) = Unit
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}