package com.polete.roundlauncher.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.system.launchUApp
import com.polete.roundlauncher.ui.components.HomePage

@Composable
fun AppNavigation(
    viewModel: MainViewModel,
    c: Context,
    modifier: Modifier
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route
    ) {

        composable(Screens.HomePage.route) {

            HomePage(
                viewModel = viewModel,
                onAppClick = { app ->
                    launchUApp(c, app)
                },
                modifier = modifier
            )

        }

    }

}