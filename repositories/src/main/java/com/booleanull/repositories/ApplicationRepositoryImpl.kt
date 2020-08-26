package com.booleanull.repositories

import android.Manifest
import android.content.pm.PackageManager
import com.booleanull.core.entity.Application
import com.booleanull.core.functional.Task
import com.booleanull.core.repository.ApplicationRepository

class ApplicationRepositoryImpl(private val packageManager: PackageManager) :
    ApplicationRepository {

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
    ): Task<java.lang.Exception, Application> {
        val packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        packages.firstOrNull {
            it.packageName == packageName
        }?.let {
            return Task.Success(
                Application(
                    packageManager.getApplicationLabel(it.applicationInfo).toString(),
                    it.packageName,
                    packageManager.getActivityIcon(packageManager.getLaunchIntentForPackage(it.packageName)!!)
                )
            )
        }
        return Task.Failure(IllegalArgumentException("Application with this ID not found"))
    }

    private fun getAllApplication(): List<Application> {
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
        }.map {
            Application(
                packageManager.getApplicationLabel(it.applicationInfo).toString(),
                it.packageName,
                packageManager.getActivityIcon(packageManager.getLaunchIntentForPackage(it.packageName)!!)
            )
        }
    }
}