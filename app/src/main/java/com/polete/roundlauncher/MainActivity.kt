package com.polete.roundlauncher

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.polete.roundlauncher.navigation.AppNavigation
import com.polete.roundlauncher.system.RLBroadcastReceiver
import com.polete.roundlauncher.ui.theme.RoundLauncherTheme

class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var broadcastReceiver: RLBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Joder vaya pérdida de tiempo para luego descubrir que simplemente puedo poner esto...
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        broadcastReceiver = RLBroadcastReceiver(this, viewModel)

        enableEdgeToEdge()
        setContent {
            RoundLauncherTheme {
                AppNavigation(viewModel, this, Modifier.fillMaxSize().systemBarsPadding())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addAction(Intent.ACTION_PACKAGE_CHANGED)
            addDataScheme("package")
        }
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }



}

