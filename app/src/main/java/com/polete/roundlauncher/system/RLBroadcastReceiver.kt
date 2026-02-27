package com.polete.roundlauncher.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.polete.roundlauncher.Container
import kotlinx.coroutines.launch


class RLBroadcastReceiver() : BroadcastReceiver() {

    private val scope = Container.scope
    private val appCache = Container.appCache

    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REMOVED,
            Intent.ACTION_PACKAGE_CHANGED -> {
                onAppsChange() // actualizar lista de apps
            }
        }

    }

    private fun onAppsChange() {
        scope.launch {
            appCache.clearCache()
            appCache.getApps()
            Container.appsChangedFlow.emit(Unit)
        }
    }
}