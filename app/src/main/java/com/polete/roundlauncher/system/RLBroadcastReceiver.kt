package com.polete.roundlauncher.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.polete.roundlauncher.Container
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class RLBroadcastReceiver() : BroadcastReceiver() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val appCache = Container.appCache

    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REMOVED,
            Intent.ACTION_PACKAGE_CHANGED -> {
                context?.let { onAppsChange(it) } // actualizar lista de apps
            }
        }

    }

    private fun onAppsChange(c: Context) {
        scope.launch {
            appCache.clearCache()
            appCache.getApps(c)
            Container.appsChangedFlow.emit(Unit)
        }
    }
}