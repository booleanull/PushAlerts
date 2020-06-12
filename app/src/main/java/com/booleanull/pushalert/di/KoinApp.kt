package com.booleanull.pushalert.di

import androidx.room.Room
import com.booleanull.core.gateway.ApplicationGateway
import com.booleanull.core.ApplicationDatabase
import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core_ui.base.BaseRouter
import com.booleanull.pushalert.repository.AlarmRepository
import com.booleanull.pushalert.repository.ApplicationRepository
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val appModule = module {
    single { Cicerone.create(BaseRouter()) }
    single { get<Cicerone<BaseRouter>>().router }
    single { get<Cicerone<BaseRouter>>().navigatorHolder }

    single {
        Room.databaseBuilder(get(), ApplicationDatabase::class.java, "ApplicationDatabase").build()
    }

    single<ApplicationGateway> { ApplicationRepository() }
    single<AlarmGateway> { AlarmRepository(get()) }
}