package com.polete.roundlauncher.system.cache

import android.content.Context
import android.content.pm.LauncherApps
import android.util.LruCache
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.polete.roundlauncher.data.UApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IconCache(private val c: Context) {

    private val cache = LruCache<String, ImageBitmap>(200)

    suspend fun getIcon(uApp: UApp): ImageBitmap = withContext(Dispatchers.IO) {
        val key = "${uApp.packageName}-${uApp.user.hashCode()}"
        cache.get(key)?.let { return@withContext it }

        val lam = c.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
        val drawable = lam.getActivityList(uApp.packageName, uApp.user)
            .firstOrNull()?.getIcon(0)
            ?: c.getDrawable(android.R.drawable.sym_def_app_icon)!!

        val bitmap = drawable.toBitmap().asImageBitmap()
        cache.put(key, bitmap)
        return@withContext bitmap
    }

}