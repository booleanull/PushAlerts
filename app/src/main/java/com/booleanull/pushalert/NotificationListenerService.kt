package com.booleanull.pushalert

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.booleanull.feature_home.interactor.SearchAlarmUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.inject

class NotificationListenerService : NotificationListenerService() {

    private val searchAlarmUseCase: SearchAlarmUseCase by inject()

    private val supervisorJob = SupervisorJob()
    private val job = CoroutineScope(supervisorJob)

    init {
        searchAlarmUseCase.join(job)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        super.onNotificationPosted(sbn, rankingMap)
        sbn?.let {
            searchAlarmUseCase.invoke(SearchAlarmUseCase.Params(sbn.packageName), onResult = { task ->
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
        startActivity(Intent().apply {
            action = Intent.ACTION_MAIN
            setClassName(packageName, "$packageName.MainActivity")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("Route", "Alarm")
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        supervisorJob.cancel()
    }
}