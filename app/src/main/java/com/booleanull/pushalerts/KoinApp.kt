package com.booleanull.pushalerts

import androidx.room.Room
import com.booleanull.core.permission.DefaultPermissionController
import com.booleanull.core.permission.PermissionController
import com.booleanull.core.repository.AlarmRepository
import com.booleanull.core.repository.ApplicationRepository
import com.booleanull.core_ui.base.BaseRouter
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import com.booleanull.database.ApplicationDatabase
import com.booleanull.repositories.AlarmRepositoryImpl
import com.booleanull.repositories.ApplicationRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val appModule = module {
    single { Cicerone.create(BaseRouter(get())) }
    single { get<Cicerone<BaseRouter>>().router }
    single { get<Cicerone<BaseRouter>>().navigatorHolder }
    single<NavigationDeepLinkHandler> { ApplicationNavigationDeepLinkHandler() }

    single {
        Room
            .databaseBuilder(get(), ApplicationDatabase::class.java, BuildConfig.DATABASE_FILE_NAME)
            .build()
    }

    single<PermissionController> { DefaultPermissionController(androidContext()) }

    single<ApplicationRepository> { ApplicationRepositoryImpl() }
    single<AlarmRepository> { AlarmRepositoryImpl(get()) }
}