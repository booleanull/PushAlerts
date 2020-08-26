package org.booleanull.pushalerts

import android.content.Intent
import android.os.CountDownTimer
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.booleanull.core.facade.SettingsFacade
import org.booleanull.core_ui.handler.NavigationDeepLinkHandler
import org.booleanull.feature_home.interactor.SearchAlarmUseCase
import org.koin.android.ext.android.inject


class NotificationListenerService : NotificationListenerService() {

    private val searchAlarmUseCase: SearchAlarmUseCase by inject()
    private val settingsFacade: SettingsFacade by inject()

    private val supervisorJob = SupervisorJob()
    private val job = CoroutineScope(supervisorJob)

    private var timer: CountDownTimer? = null
    private var blockAlarm = false

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

    private fun onPushIntercepted(sbn: StatusBarNotification, rankingMap: RankingMap?) {
        if (!blockAlarm) {
            blockAlarm = true
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(
                NavigationDeepLinkHandler.DEEP_LINK,
                NavigationDeepLinkHandler.ALARM_FRAGMENT
            )
            intent.putExtra(NavigationDeepLinkHandler.PACKAGE_NAME, sbn.packageName)
            startActivity(intent)
            timer = object : CountDownTimer(30000L, 1000L) {
                override fun onFinish() {
                    blockAlarm = false
                }

                override fun onTick(p0: Long) = Unit
            }.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        supervisorJob.cancel()
    }
}