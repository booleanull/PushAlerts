package org.booleanull.pushalerts.facade

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import org.booleanull.core.facade.AnalyticsFacade

class AnalyticsFacadeImpl(private val firebaseAnalytics: FirebaseAnalytics) : AnalyticsFacade {

    override fun sendEvent(event: String, bundle: Bundle?) {
        firebaseAnalytics.logEvent(event, bundle)
    }
}