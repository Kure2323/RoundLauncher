package com.polete.roundlauncher.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.ui.drawpage.DrawerPage
import com.polete.roundlauncher.ui.homepage.HomePage
import com.polete.roundlauncher.ui.settingspage.SettingsPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route
    ) {

        composable(Screens.HomePage.route) {

            if (sheetState.isVisible) {
                ModalBottomSheet(
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()
                        }
                    },
                    containerColor = Color.Black.copy(alpha = 0.3f),
                    sheetState = sheetState,
                    contentWindowInsets = { WindowInsets.statusBars },
                    dragHandle = null
                ) {
                    DrawerPage(
                        viewModel = viewModel,
                        sheetState = sheetState
                    )
                }

            }

            HomePage(
                onBackgroundClick = {
                    scope.launch {
                        sheetState.show()
                    }
                }
            )

        }

        composable(Screens.Settings.route) {

            SettingsPage(

            )

        }

    }

}