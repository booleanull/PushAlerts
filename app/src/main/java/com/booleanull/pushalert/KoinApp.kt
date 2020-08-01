package com.booleanull.pushalert

import androidx.room.Room
import com.booleanull.core.gateway.AlarmGateway
import com.booleanull.core.gateway.ApplicationGateway
import com.booleanull.core_ui.base.BaseRouter
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import com.booleanull.core_ui.permission.AllPermissionController
import com.booleanull.core_ui.permission.PermissionController
import com.booleanull.database.ApplicationDatabase
import com.booleanull.repositories.AlarmRepository
import com.booleanull.repositories.ApplicationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val appModule = module {
    single { Cicerone.create(BaseRouter(get())) }
    single { get<Cicerone<BaseRouter>>().router }
    single { get<Cicerone<BaseRouter>>().navigatorHolder }
    single<NavigationDeepLinkHandler> { ApplicationNavigationDeepLinkHandler() }

    single {
        Room.databaseBuilder(
            get(),
            ApplicationDatabase::class.java,
            BuildConfig.DATABASE_FILE_NAME
        ).build()
    }

    single<PermissionController> { AllPermissionController(androidContext()) }

    single<ApplicationGateway> { ApplicationRepository() }
    single<AlarmGateway> { AlarmRepository(get()) }
}