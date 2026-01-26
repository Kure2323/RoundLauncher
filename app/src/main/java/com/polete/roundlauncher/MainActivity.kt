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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polete.roundlauncher.navigation.Screens
import com.polete.roundlauncher.system.LauncherEvents
import com.polete.roundlauncher.system.RLBroadcastReceiver
import com.polete.roundlauncher.system.cache.AppCache
import com.polete.roundlauncher.system.cache.IconCache
import com.polete.roundlauncher.system.launchUApp
import com.polete.roundlauncher.ui.components.Drawer
import com.polete.roundlauncher.ui.theme.RoundLauncherTheme

class MainActivity : ComponentActivity() {

    //ViewModel
    lateinit var viewModel: MainViewModel

    //Broadcast
    lateinit var broadcastReceiver: RLBroadcastReceiver

    //Database and DAO
//    lateinit var db: AppDatabase
//    lateinit var appDao: AppDao
//    lateinit var appRepo: AppRepository

    lateinit var scope: LauncherEvents

    //Cache
    lateinit var iconCache: IconCache
    lateinit var appCache: AppCache

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Joder vaya pérdida de tiempo para luego descubrir que simplemente puedo poner esto...
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)

        /*
        All util variables
         */
        
        //Scope
        scope = LauncherEvents
        //Data
//        db = AppDatabase.getDatabase(this)
//        appDao = db.appDao()
//        appRepo = AppRepository(appDao)

        //Cache
        appCache = AppCache(this)
        iconCache = IconCache(this)

        //Broadcaster
        broadcastReceiver = RLBroadcastReceiver(
            icache = iconCache,
            acache = appCache,
            scope = scope
        )

        //ViewModel
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(
                iconCache,
                appCache
            ) //Aquí
        )[MainViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            RoundLauncherTheme {

                val navController = rememberNavController()


                NavHost(
                    navController = navController,
                    startDestination = Screens.HomePage.route
                ) {

                    composable(Screens.HomePage.route) {

                        Drawer(
                            viewModel = viewModel,
                            searchBar = {  },
                            onAppClick = { launchUApp(this@MainActivity, it) },
                            onSettingsClick = { }
                        )

                    }

                }

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

