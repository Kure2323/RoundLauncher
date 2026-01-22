package com.polete.roundlauncher.system

import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.UserManager
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.data.entities.AppEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.graphics.createBitmap

/**
 * Returns the list of Apps without any order
 */
suspend fun getAppList(c: Context) : List<UApp> = withContext(Dispatchers.IO){

    val appList = mutableListOf<UApp>()

    val userManager = c.getSystemService(Context.USER_SERVICE) as UserManager
    val lam = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

    for (user in userManager.userProfiles) {

        for (app in lam.getActivityList(null, user)) {

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
    return@withContext appList

}

fun formalizeApp(c: Context, appEntity: AppEntity): UApp {
    val lam = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    val urm = c.getSystemService(Context.USER_SERVICE) as UserManager

    val pkgname = appEntity.pkgname
    val user = urm.getUserForSerialNumber(appEntity.userSerialNumber)

    val activityInfo = lam.getActivityList(pkgname, user).first()

    return UApp(
        packageName = pkgname,
        label = activityInfo.label.toString(),
        componentName = activityInfo.componentName,
        user = user
    )

}

fun launchUApp(c: Context, app: UApp) {
    val launcherApps = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    try {
        launcherApps.startMainActivity(app.componentName, app.user, null, null)
    } catch (_: Exception) { }
}

suspend fun drawableToBitmap(drawable: Drawable): Bitmap = withContext(Dispatchers.IO) {
    if (drawable is BitmapDrawable) {
        drawable.bitmap?.let { return@let it }
    }

    val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 72
    val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 72

    val bitmap = createBitmap(width, height)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return@withContext bitmap
}