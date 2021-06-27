package org.booleanull.pushalerts

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import org.booleanull.core.facade.AnalyticsFacade
import org.koin.core.KoinComponent

class AnalyticsFacadeImpl(private val firebaseAnalytics: FirebaseAnalytics) : AnalyticsFacade,
    KoinComponent {

    override fun sendEvent(event: String, bundle: Bundle?) {
        firebaseAnalytics.logEvent(event, bundle)
    }
}