package com.polete.roundlauncher.system

import android.app.WallpaperManager
import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.drawable.Drawable
import android.os.UserManager
import androidx.core.graphics.drawable.toBitmap
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

/**
 * Returns the Bitmap of the wallpaper in use
 */
suspend fun getWallpaperBitMap(c: Context) : Bitmap? = withContext(Dispatchers.Default) {

    val drawable = getWallpaperDrawable(c)

    return@withContext drawable?.toBitmap(
        width = drawable.intrinsicWidth,
        height = drawable.intrinsicHeight,
        config = Config.ARGB_8888
    )

}

/**
 * Just to get the wallpaper drawable
 */
private fun getWallpaperDrawable(c: Context) : Drawable? {
    val wpManager = WallpaperManager.getInstance(c)
    return try {
        wpManager.drawable
    } catch (_: SecurityException) {
        null
    }
}



fun launchApp(c: Context, appModule: AppModule) {
    val launcherApps = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    try {
        launcherApps.startMainActivity(appModule.componentName, appModule.user, null, null)
    } catch (_: Exception) { }
}