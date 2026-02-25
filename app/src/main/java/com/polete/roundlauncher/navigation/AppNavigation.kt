package com.polete.roundlauncher.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.ui.drawpage.DrawerPage
import com.polete.roundlauncher.ui.homepage.HomePage

@Composable
fun AppNavigation(c: Context) {

    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route
    ) {

        composable(Screens.DrawPage.route) {

            DrawerPage(
                navController,
                viewModel = viewModel,
                modifier = Modifier,
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