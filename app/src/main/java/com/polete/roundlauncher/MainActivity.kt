package com.polete.roundlauncher

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.polete.roundlauncher.navigation.AppNavigation
import com.polete.roundlauncher.system.RLBroadcastReceiver
import com.polete.roundlauncher.ui.theme.RoundLauncherTheme

class MainActivity : ComponentActivity() {

    //Broadcast
    lateinit var broadcastReceiver: RLBroadcastReceiver

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Container.init(this)

        // Joder vaya pérdida de tiempo para luego descubrir que simplemente puedo poner esto...
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)

        //Broadcaster
        broadcastReceiver = RLBroadcastReceiver()

        enableEdgeToEdge()
        setContent {
            RoundLauncherTheme {
                AppNavigation(this)
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

