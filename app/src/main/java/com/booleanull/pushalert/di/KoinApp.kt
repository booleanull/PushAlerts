package com.booleanull.pushalert.di

import androidx.room.Room
import com.booleanull.repositories.ApplicationDatabase
import com.booleanull.core.Configuration
import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.gateway.ApplicationGateway
import com.booleanull.core_ui.base.BaseRouter
import com.booleanull.core_ui.handler.NavigationDeeplinkHandler
import com.booleanull.pushalert.GlobalNavigationDeeplinkHandler
import com.booleanull.repositories.AlarmRepository
import com.booleanull.repositories.ApplicationRepository
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val appModule = module {
    single { Cicerone.create(BaseRouter(get())) }
    single { get<Cicerone<BaseRouter>>().router }
    single { get<Cicerone<BaseRouter>>().navigatorHolder }
    single<NavigationDeeplinkHandler> { GlobalNavigationDeeplinkHandler() }

    single {
        Room.databaseBuilder(get(), ApplicationDatabase::class.java, "ApplicationDatabase").build()
    }
    single { Configuration(true) }

    single<ApplicationGateway> { ApplicationRepository() }
    single<AlarmGateway> { AlarmRepository(get()) }
}