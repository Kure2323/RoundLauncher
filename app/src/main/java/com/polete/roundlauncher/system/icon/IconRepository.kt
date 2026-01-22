package com.polete.roundlauncher.system.icon

import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.drawable.Drawable
import android.util.LruCache
import com.polete.roundlauncher.data.UApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IconRepository(private val context: Context) {

    private val cache = LruCache<String, Drawable>(100)

    suspend fun getIcon(uApp: UApp): Drawable = withContext(Dispatchers.IO) {
        val key = "${uApp.packageName}-${uApp.user.hashCode()}"
        cache.get(key)?.let { return@let it }

        val lam = context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
        val drawable = lam.getActivityList(uApp.packageName, uApp.user)
            .first()
            .getIcon(0)
        cache.put(key, drawable)
        return@withContext drawable
    }
}
