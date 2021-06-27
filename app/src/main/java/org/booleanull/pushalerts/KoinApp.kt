package org.booleanull.pushalerts

import androidx.room.Room
import com.google.firebase.analytics.FirebaseAnalytics
import org.booleanull.core.facade.AnalyticsFacade
import org.booleanull.core.facade.SettingsFacade
import org.booleanull.core.facade.ThemeFacade
import org.booleanull.core.permission.PermissionCompositeController
import org.booleanull.core.permission.PermissionCompositeControllerImpl
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.core.repository.ApplicationRepository
import org.booleanull.core_ui.base.Router
import org.booleanull.core_ui.handler.NavigationDeepLinkHandler
import org.booleanull.database.ApplicationDatabase
import org.booleanull.database.Migrations.MIGRATION_1_2
import org.booleanull.database.Migrations.MIGRATION_2_3
import org.booleanull.pushalerts.facade.AnalyticsFacadeImpl
import org.booleanull.pushalerts.facade.SettingsFacadeImpl
import org.booleanull.pushalerts.facade.ThemeFacadeImpl
import org.booleanull.pushalerts.handler.ApplicationNavigationDeepLinkHandler
import org.booleanull.repositories.AlarmRepositoryImpl
import org.booleanull.repositories.ApplicationRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val appModule = module {
    single { Cicerone.create(Router(get())) }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().navigatorHolder }
    single<NavigationDeepLinkHandler> { ApplicationNavigationDeepLinkHandler() }

    single { FirebaseAnalytics.getInstance(androidContext()) }

    single {
        Room
            .databaseBuilder(get(), ApplicationDatabase::class.java, BuildConfig.DATABASE_FILE_NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    single<PermissionCompositeController> { PermissionCompositeControllerImpl(androidContext()) }
    single<SettingsFacade> { SettingsFacadeImpl(androidContext()) }
    single<ThemeFacade> { ThemeFacadeImpl(get()) }
    single<AnalyticsFacade> { AnalyticsFacadeImpl(get()) }

    single<ApplicationRepository> {
        ApplicationRepositoryImpl(
            androidContext().packageManager,
            get()
        )
    }
    single<AlarmRepository> { AlarmRepositoryImpl(get()) }
}