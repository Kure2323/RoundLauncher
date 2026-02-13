package com.polete.roundlauncher.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polete.roundlauncher.system.launchUApp
import com.polete.roundlauncher.ui.components.Drawer

@Composable
fun AppNavigation(c: Context) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route
    ) {

        composable(Screens.HomePage.route) {

            Drawer(
                searchBar = {  },
                onAppClick = { launchUApp(c, it) },
                onSettingsClick = { }
            )

        }

    }

}