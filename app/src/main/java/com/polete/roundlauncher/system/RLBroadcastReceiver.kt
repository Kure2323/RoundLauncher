package com.polete.roundlauncher.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.polete.roundlauncher.Container
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RLBroadcastReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val appCache = Container.appCache

    override fun onReceive(context: Context?, intent: Intent?) {

        val pending = goAsync()

        when (intent?.action) {
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REMOVED,
            Intent.ACTION_PACKAGE_CHANGED,
            Intent.ACTION_PACKAGE_REPLACED -> {
                context?.let {
                    scope.launch {
                        try {
                            onAppsChange(it)
                            Container.appsChangedFlow.value++
                        } finally {
                            pending.finish()
                        }
                    }
                } ?: pending.finish()
            }
            else -> pending.finish()
        }

    }

    private suspend fun onAppsChange(c: Context) {
        appCache.clearCache()
        delay(500)
        appCache.getApps(c)
    }
}