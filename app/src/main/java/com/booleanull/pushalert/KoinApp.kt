package com.booleanull.pushalert

import androidx.room.Room
import com.booleanull.repositories.ApplicationDatabase
import com.booleanull.core.Configuration
import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.gateway.ApplicationGateway
import com.booleanull.core_ui.base.BaseRouter
import com.booleanull.core_ui.handler.NavigationDeeplinkHandler
import com.booleanull.repositories.AlarmRepository
import com.booleanull.repositories.ApplicationRepository
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val appModule = module {
    single { Configuration(true) }

    single { Room.databaseBuilder(get(), ApplicationDatabase::class.java, BuildConfig.DATABASE_FILE_NAME).build() }

    single { Cicerone.create(BaseRouter(get())) }
    single { get<Cicerone<BaseRouter>>().router }
    single { get<Cicerone<BaseRouter>>().navigatorHolder }
    single<NavigationDeeplinkHandler> { ApplicationNavigationDeeplinkHandler() }

    single<ApplicationGateway> { ApplicationRepository() }
    single<AlarmGateway> { AlarmRepository(get()) }
}