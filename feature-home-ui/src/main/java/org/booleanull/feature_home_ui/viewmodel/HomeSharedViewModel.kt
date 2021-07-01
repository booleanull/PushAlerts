package org.booleanull.feature_home_ui.viewmodel

import androidx.lifecycle.LiveData
import org.booleanull.core_ui.helper.SingleLiveEvent

internal class HomeSharedViewModel {

    private val updateInternal = SingleLiveEvent<Unit>()
    val update: LiveData<Unit>
        get() = updateInternal

    fun update() {
        updateInternal.call()
    }
}