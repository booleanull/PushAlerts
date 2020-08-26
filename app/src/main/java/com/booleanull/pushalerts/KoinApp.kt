package com.booleanull.pushalerts

import androidx.room.Room
import com.booleanull.core.facade.SettingsFacade
import com.booleanull.core.facade.ThemeFacade
import com.booleanull.core.permission.PermissionCompositeController
import com.booleanull.core.permission.PermissionCompositeControllerImpl
import com.booleanull.core.repository.AlarmRepository
import com.booleanull.core.repository.ApplicationRepository
import com.booleanull.core_ui.base.Router
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import com.booleanull.database.ApplicationDatabase
import com.booleanull.repositories.AlarmRepositoryImpl
import com.booleanull.repositories.ApplicationRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val appModule = module {
    single { Cicerone.create(Router(get())) }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().navigatorHolder }
    single<NavigationDeepLinkHandler> { ApplicationNavigationDeepLinkHandler() }

    single {
        Room
            .databaseBuilder(get(), ApplicationDatabase::class.java, BuildConfig.DATABASE_FILE_NAME)
            .build()
    }

    single<PermissionCompositeController> { PermissionCompositeControllerImpl(androidContext()) }
    single<SettingsFacade> { SettingsFacadeImpl(androidContext()) }
    single<ThemeFacade> { ThemeFacadeImpl(get()) }

    single<ApplicationRepository> { ApplicationRepositoryImpl(androidContext().packageManager) }
    single<AlarmRepository> { AlarmRepositoryImpl(get()) }
}