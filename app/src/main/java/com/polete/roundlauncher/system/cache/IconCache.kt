package com.polete.roundlauncher.system.cache

import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import android.util.LruCache
import androidx.core.graphics.drawable.toBitmap
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.system.getKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IconCache {

    private val cache = object : LruCache<String, Bitmap>(300) {}
    suspend fun getIcon(uApp: UApp, c: Context): Bitmap = withContext(Dispatchers.IO) {
        val lam = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

        val key = getKey(uApp)
        cache.get(key)?.let { return@withContext it }


        val drawable = lam.getActivityList(uApp.packageName, uApp.user)
            .firstOrNull()?.getIcon(0)
            ?: c.getDrawable(android.R.drawable.sym_def_app_icon)!!

        val density = c.resources.displayMetrics.density
        val sizePx = (48 * density).toInt()

        val bitmap = drawable.toBitmap(width = sizePx, height = sizePx)
        cache.put(key, bitmap)
        return@withContext bitmap
    }

}