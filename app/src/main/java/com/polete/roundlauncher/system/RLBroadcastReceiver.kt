package com.polete.roundlauncher.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.polete.roundlauncher.system.cache.AppCache
import com.polete.roundlauncher.system.cache.IconCache
import kotlinx.coroutines.launch


class RLBroadcastReceiver(private val icache: IconCache, private val acache: AppCache, private val scope: LauncherEvents) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REMOVED,
            Intent.ACTION_PACKAGE_CHANGED -> {
                onAppsChange(acache) // actualizar lista de apps
            }
        }

    }

    private fun onAppsChange(acache: AppCache) {
        scope.scope.launch {
            acache.clearCache()
            acache.getApps()
        }
    }
}