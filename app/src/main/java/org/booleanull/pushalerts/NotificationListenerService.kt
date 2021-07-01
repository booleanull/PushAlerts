package org.booleanull.pushalerts

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.booleanull.core.facade.SettingsFacade
import org.booleanull.core_ui.handler.NavigationDeepLinkHandler
import org.booleanull.feature_home.interactor.IncrementCountAlarmUseCase
import org.booleanull.feature_home.interactor.SearchAlarmUseCase
import org.koin.android.ext.android.inject

class NotificationListenerService : NotificationListenerService() {

    private val searchAlarmUseCase: SearchAlarmUseCase by inject()
    private val incrementCountAlarmUseCase: IncrementCountAlarmUseCase by inject()
    private val settingsFacade: SettingsFacade by inject()

    private val supervisorJob = SupervisorJob()
    private val searchAlarmJob = CoroutineScope(supervisorJob)
    private val incrementCountAlarmJob = CoroutineScope(supervisorJob)
    private val unlockAlarmDelayStatusJob = CoroutineScope(supervisorJob)

    private var alarmDelayStatus = false

    init {
        searchAlarmUseCase.join(searchAlarmJob)
        incrementCountAlarmUseCase.join(incrementCountAlarmJob)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        super.onNotificationPosted(sbn, rankingMap)
        if (!settingsFacade.getAlarm()) {
            sbn?.let {
                searchAlarmUseCase.invoke(
                    SearchAlarmUseCase.Params(sbn.packageName),
                    onResult = { task ->
                        task.fold({ alarmWithFilter ->
                            if (alarmWithFilter.alarm.hasAlarm) {
                                if (!alarmWithFilter.alarm.hasFilter) {
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

    override fun onDestroy() {
        super.onDestroy()
        supervisorJob.cancel()
    }

    private fun onPushIntercepted(sbn: StatusBarNotification, rankingMap: RankingMap?) {
        if (!alarmDelayStatus) {
            incrementCountAlarmUseCase.invoke(IncrementCountAlarmUseCase.Params(sbn.packageName))
            alarmDelayStatus = true
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(
                NavigationDeepLinkHandler.DEEP_LINK,
                NavigationDeepLinkHandler.ALARM_FRAGMENT
            )
            intent.putExtra(NavigationDeepLinkHandler.PACKAGE_NAME, sbn.packageName)
            startActivity(intent)
            unlockAlarmDelayStatusJob.launch {
                unlockAlarmDelayStatus()
            }
        }
    }

    private suspend fun unlockAlarmDelayStatus() {
        delay(30000L)
        alarmDelayStatus = false
    }
}