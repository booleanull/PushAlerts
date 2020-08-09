package com.booleanull.pushalerts

import android.app.Application
import com.booleanull.core_ui.coreModule
import com.booleanull.feature_alarm_ui.alarmModule
import com.booleanull.feature_home_ui.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            androidLogger(level = Level.DEBUG)
            modules(appModule + coreModule + homeModule + alarmModule)
        }
    }
}