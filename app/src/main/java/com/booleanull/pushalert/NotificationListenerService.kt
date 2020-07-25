package com.booleanull.pushalert

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.widget.Toast
import com.booleanull.feature_home.interactor.SearchAlarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.inject

class NotificationListenerService : NotificationListenerService() {

    private val searchAlarm: SearchAlarm by inject()

    private val supervisorJob = SupervisorJob()
    private val job = CoroutineScope(supervisorJob)

    init {
        searchAlarm.join(job)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        super.onNotificationPosted(sbn, rankingMap)
        sbn?.let {
            searchAlarm.invoke(SearchAlarm.Params(sbn.packageName), onResult = { task ->
                task.fold({ alarmWithFilter ->
                    if (alarmWithFilter.alarm.isAlarm) {
                        if (!alarmWithFilter.alarm.isFilter) {
                            onPushIntercepted(sbn, rankingMap)
                        } else {
                            alarmWithFilter.filters.forEach {
                                if ((sbn.notification.extras.getString("android.title")
                                        ?: "").contains(it.filter)
                                    || (sbn.notification.extras.getString("android.text")
                                        ?: "").contains(it.filter)
                                ) {
                                    onPushIntercepted(sbn, rankingMap)
                                }
                            }
                        }
                    }
                })
            })
        }
    }

    private fun onPushIntercepted(sbn: StatusBarNotification, rankingMap: RankingMap?) {
        Toast.makeText(applicationContext, sbn.packageName, Toast.LENGTH_LONG)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        supervisorJob.cancel()
    }
}