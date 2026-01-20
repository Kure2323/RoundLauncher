package com.polete.roundlauncher.system

import android.content.Context
import android.content.pm.LauncherApps
import android.os.UserManager
import com.polete.roundlauncher.data.AppModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Returns the list of Apps without any order
 */
suspend fun getAppList(c: Context) : List<AppModule> = withContext(Dispatchers.IO){

    val appList = mutableListOf<AppModule>()

    val userManager = c.getSystemService(Context.USER_SERVICE) as UserManager
    val lam = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

    for (user in userManager.userProfiles) {

        for (app in lam.getActivityList(null, user)) {

            appList.add(
                AppModule(
                    label = app.label.toString(),
                    packageName = app.applicationInfo.packageName,
                    drawable = app.getIcon(0),
                    componentName = app.componentName,
                    user = user
                )
            )

        }

    }
    return@withContext appList

}

fun launchApp(c: Context, appModule: AppModule) {
    val launcherApps = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    try {
        launcherApps.startMainActivity(appModule.componentName, appModule.user, null, null)
    } catch (_: Exception) { }
}