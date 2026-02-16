package com.polete.roundlauncher.system

import android.content.Context
import android.content.pm.LauncherApps
import android.widget.Toast
import com.polete.roundlauncher.R
import com.polete.roundlauncher.data.UApp


//fun formalizeApp(c: Context, appEntity: AppEntity): UApp {
//    val lam = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
//    val urm = c.getSystemService(Context.USER_SERVICE) as UserManager
//
//    val pkgname = appEntity.pkgname
//    val user = urm.getUserForSerialNumber(appEntity.userSerialNumber)
//
//    val activityInfo = lam.getActivityList(pkgname, user).first()
//
//    return UApp(
//        packageName = pkgname,
//        label = activityInfo.label.toString(),
//        componentName = activityInfo.componentName,
//        user = user
//    )
//
//}

fun launchUApp(c: Context, app: UApp) {
    val launcherApps = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    try {
        launcherApps.startMainActivity(app.componentName, app.user, null, null)
    } catch (_: Exception) {
        Toast.makeText(c, R.string.app_launch_error,Toast.LENGTH_SHORT).show()
    }
}
