package com.polete.roundlauncher.navigation

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.system.launchUApp
import com.polete.roundlauncher.ui.drawpage.DrawerPage
import com.polete.roundlauncher.ui.homepage.HomePage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(c: Context) {

    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    val icons = remember { mutableStateMapOf<String, Bitmap>() }
    var appList by remember { mutableStateOf<List<UApp>>(emptyList()) }

    LaunchedEffect(Unit) {

        appList = viewModel.getApps()

        coroutineScope {
            appList.forEach { app ->

                val key = "${app.packageName}-${app.user.hashCode()}"

                if (!icons.containsKey(key)) {

                    launch {
                        val icon = viewModel.getIcon(app)
                        icons[key] = icon
                    }

                }
            }
        }
    }


    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route
    ) {

        composable(Screens.DrawPage.route) {

            DrawerPage(
                navController,
                viewModel = viewModel,
                modifier = Modifier,
                appList = appList,
                icons = icons
            )

        }

        composable(Screens.HomePage.route) {

            HomePage(
                navController
            )

        }

        composable(Screens.Settings.route) {

            TODO()

        }

    }

}