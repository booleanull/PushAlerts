package com.booleanull.sleepork

import android.app.Application
import com.booleanull.feature_home_ui.di.homeModule
import com.booleanull.sleepork.di.appModule
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
            modules(appModule + homeModule)
        }
    }
}
