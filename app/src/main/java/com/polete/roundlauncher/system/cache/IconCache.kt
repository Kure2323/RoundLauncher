package com.polete.roundlauncher.system.cache

import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.util.LruCache
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.polete.roundlauncher.data.UApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IconCache(private val c: Context) {

    private val cache = object : LruCache<String, Bitmap>(300) {}
    private val lam = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
    suspend fun getIcon(uApp: UApp): Bitmap = withContext(Dispatchers.IO) {
        val key = "${uApp.packageName}-${uApp.user.hashCode()}"
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