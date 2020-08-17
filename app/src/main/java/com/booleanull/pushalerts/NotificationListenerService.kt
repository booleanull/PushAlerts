package com.booleanull.pushalerts

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.booleanull.core.facade.SettingsFacade
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import com.booleanull.feature_home.interactor.SearchAlarmUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.inject


class NotificationListenerService : NotificationListenerService() {

    private val searchAlarmUseCase: SearchAlarmUseCase by inject()
    private val settingsFacade: SettingsFacade by inject()

    private val supervisorJob = SupervisorJob()
    private val job = CoroutineScope(supervisorJob)

    init {
        searchAlarmUseCase.join(job)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        super.onNotificationPosted(sbn, rankingMap)
        if (!settingsFacade.getAlarm()) {
            sbn?.let {
                searchAlarmUseCase.invoke(
                    SearchAlarmUseCase.Params(sbn.packageName),
                    onResult = { task ->
                        task.fold({ alarmWithFilter ->
                            if (alarmWithFilter.alarm.isAlarm) {
                                if (!alarmWithFilter.alarm.isFilter) {
                                    onPushIntercepted(sbn, rankingMap)
                                } else {
                                    alarmWithFilter.filters.forEach {
                                        if ((sbn.notification.extras.getString("android.title")
                                                ?: "").contains(it.filter, true)
                                            || (sbn.notification.extras.getString("android.text")
                                                ?: "").contains(it.filter, true)
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
    }

    private fun onPushIntercepted(sbn: StatusBarNotification, rankingMap: RankingMap?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(
            NavigationDeepLinkHandler.DEEP_LINK,
            NavigationDeepLinkHandler.ALARM_FRAGMENT
        )
        intent.putExtra(NavigationDeepLinkHandler.PACKAGE_NAME, sbn.packageName)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        supervisorJob.cancel()
    }
}