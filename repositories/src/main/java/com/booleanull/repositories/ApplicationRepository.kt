package com.booleanull.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import com.booleanull.core.dto.ApplicationDTO
import com.booleanull.core.gateway.ApplicationGateway
import com.booleanull.core.functional.Task

class ApplicationRepository : ApplicationGateway {

    override suspend fun getApplicationList(context: Context): List<ApplicationDTO> {
        val packageManager = context.packageManager

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
            ApplicationDTO(
                packageManager.getApplicationLabel(it.applicationInfo).toString(),
                it.packageName,
                packageManager.getActivityIcon(packageManager.getLaunchIntentForPackage(it.packageName)!!)
            )
        }
    }

    override suspend fun searchApplicationList(context: Context, query: String): Task<Exception, List<ApplicationDTO>> {
        val packageManager = context.packageManager

        val packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        val filteredPackages = packages.filter {
            var hasInternetPermission = false
            packageManager.getPackageInfo(it.packageName, PackageManager.GET_PERMISSIONS)
                .requestedPermissions?.let { permissions ->
                permissions.forEach { permission ->
                    if (permission == Manifest.permission.INTERNET) hasInternetPermission = true
                }
            }
            packageManager.getLaunchIntentForPackage(it.packageName) != null && hasInternetPermission
        }.map {
            ApplicationDTO(
                packageManager.getApplicationLabel(it.applicationInfo).toString(),
                it.packageName,
                packageManager.getActivityIcon(packageManager.getLaunchIntentForPackage(it.packageName)!!)
            )
        }.filter {
            it.name.startsWith(query, true) || it.packageName.startsWith(query, true)
        }

        return if(filteredPackages.isNotEmpty()) {
            Task.Success(filteredPackages)
        } else {
            Task.Failure(IllegalArgumentException("Applications not found"))
        }
    }

    override suspend fun getApplication(
        context: Context,
        packageName: String
    ): Task<java.lang.Exception, ApplicationDTO> {
        val packageManager = context.packageManager

        val packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        packages.firstOrNull {
            it.packageName == packageName
        }?.let {
            return Task.Success(
                ApplicationDTO(
                    packageManager.getApplicationLabel(it.applicationInfo).toString(),
                    it.packageName,
                    packageManager.getActivityIcon(packageManager.getLaunchIntentForPackage(it.packageName)!!)
                )
            )
        }
        return Task.Failure(IllegalArgumentException("Application with this ID not found"))
    }
}