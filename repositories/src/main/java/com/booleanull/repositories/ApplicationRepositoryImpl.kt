package org.booleanull.repositories

import android.Manifest
import android.content.pm.PackageManager
import org.booleanull.core.entity.Application
import org.booleanull.core.functional.Task
import org.booleanull.core.repository.AlarmRepository
import org.booleanull.core.repository.ApplicationRepository

class ApplicationRepositoryImpl(
    private val packageManager: PackageManager,
    private val alarmRepository: AlarmRepository
) : ApplicationRepository {

    override suspend fun getApplicationList(): List<Application> {
        return getAllApplication()
    }

    override suspend fun searchApplicationList(
        query: String
    ): Task<Exception, List<Application>> {
        val filteredPackages = getAllApplication().filter {
            it.name.contains(query, true) || it.packageName.contains(query, true)
        }

        return if (filteredPackages.isNotEmpty()) {
            Task.Success(filteredPackages)
        } else {
            Task.Failure(IllegalArgumentException("Applications not found"))
        }
    }

    override suspend fun getApplication(
        packageName: String
    ): Task<Exception, Application> {
        val packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        packages.firstOrNull {
            it.packageName == packageName
        }?.let {
            return Task.Success(
                Application(
                    packageManager.getApplicationLabel(it.applicationInfo).toString(),
                    it.packageName,
                    packageManager.getActivityIcon(packageManager.getLaunchIntentForPackage(it.packageName)!!),
                    alarmRepository.searchAlarm(it.packageName).fold({ alarm ->
                        alarm.alarm.isFavorite
                    }, {
                        false
                    })
                )
            )
        }
        return Task.Failure(IllegalArgumentException("Application with this ID not found"))
    }

    private suspend fun getAllApplication(): List<Application> {
        val alarms = alarmRepository.getAlarms().map { it.alarm }
        val packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        return packages.filter {
            var hasInternetPermission = false
            packageManager.getPackageInfo(it.packageName, PackageManager.GET_PERMISSIONS)
                .requestedPermissions?.let { permissions ->
                    permissions.forEach { permission ->
                        if (permission == Manifest.permission.INTERNET) hasInternetPermission = true
                    }
                }
            packageManager.getLaunchIntentForPackage(it.packageName) != null && hasInternetPermission
        }.map { packageInfo ->
            Application(
                packageManager.getApplicationLabel(packageInfo.applicationInfo).toString(),
                packageInfo.packageName,
                packageManager.getActivityIcon(packageManager.getLaunchIntentForPackage(packageInfo.packageName)!!),
                alarms.find { alarm ->
                    alarm.packageName == packageInfo.packageName
                }?.isFavorite ?: false
            )
        }
    }
}