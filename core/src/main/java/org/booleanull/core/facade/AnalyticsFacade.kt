package org.booleanull.core.facade

import android.os.Bundle

interface AnalyticsFacade {

    fun sendEvent(send: String, bundle: Bundle? = null)
}