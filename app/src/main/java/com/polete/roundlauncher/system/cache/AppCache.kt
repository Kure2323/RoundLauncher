package com.polete.roundlauncher.system.cache

import android.content.Context
import android.content.pm.LauncherApps
import android.os.UserManager
import com.polete.roundlauncher.data.UApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppCache(private val c: Context) {


    private var cacheApps: List<UApp>? = null

    suspend fun getApps(): List<UApp> = withContext(Dispatchers.IO) {

        cacheApps?.let { return@withContext it }

        val launcherApps = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
        val userManager = c.getSystemService(Context.USER_SERVICE) as UserManager

        val appList = userManager.userProfiles.flatMap { user ->
            launcherApps.getActivityList(null, user).map { app ->
                UApp(
                    label = app.label.toString(),
                    packageName = app.applicationInfo.packageName,
                    componentName = app.componentName,
                    user = user
                )
            }
        }

        cacheApps = appList
        appList
    }

    fun clearCache() {
        cacheApps = null
    }

}