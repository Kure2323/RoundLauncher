package com.polete.roundlauncher.system.cache

import android.content.Context
import android.content.pm.LauncherApps
import android.os.UserManager
import com.polete.roundlauncher.data.UApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppCache(c: Context) {

    private val launcherApps = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    private val userManager = c.getSystemService(Context.USER_SERVICE) as UserManager

    private var cacheApps: List<UApp>? = null

    suspend fun getApps(): List<UApp> {

        cacheApps?.let { return it }

        return withContext(Dispatchers.IO) {

            val appList = mutableListOf<UApp>()

            for (user in userManager.userProfiles) {

                for (app in launcherApps.getActivityList(null, user)) {

                    appList.add(
                        UApp(
                            label = app.label.toString(),
                            packageName = app.applicationInfo.packageName,
                            componentName = app.componentName,
                            user = user
                        )
                    )

                }

            }

            cacheApps = appList // Save on Ram :)))
            return@withContext appList

        }
    }

    fun clearCache() {
        cacheApps = null
    }

}