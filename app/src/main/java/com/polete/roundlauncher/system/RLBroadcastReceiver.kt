package com.polete.roundlauncher.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.polete.roundlauncher.MainViewModel


class RLBroadcastReceiver(private val c: Context, private val viewModel: MainViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REMOVED,
            Intent.ACTION_PACKAGE_CHANGED -> {
                onAppsChange(viewModel) // actualizar lista de apps
            }
        }

    }

    private fun onAppsChange(viewModel: MainViewModel) {
        viewModel.loadApps()
    }
}