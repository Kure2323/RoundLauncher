package com.polete.roundlauncher.navigation

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polete.roundlauncher.Container
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.ui.drawpage.DrawerPage
import com.polete.roundlauncher.ui.homepage.RoundLauncher
import com.polete.roundlauncher.ui.settingspage.SettingsK
import com.polete.roundlauncher.ui.settingspage.SettingsPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(c: Context) {

    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var settings: SettingsK by remember {
        mutableStateOf(Container.settings)
    }


    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route
    ) {

        composable(Screens.HomePage.route) {

            // Blocks the 'go back' action
            BackHandler(enabled = true) { }

            if (sheetState.isVisible) {
                ModalBottomSheet(
                    // When the sheetState turns to hide
                    onDismissRequest = {},
                    containerColor = Color.Black.copy(alpha = 0.3f),
                    sheetState = sheetState,
                    contentWindowInsets = { WindowInsets.statusBars },
                    dragHandle = null
                ) {
                    DrawerPage(
                        viewModel = viewModel,
                        sheetState = sheetState,
                        settingsButton = {
                            navController.navigate(Screens.Settings.route)
                        },
                        settings = settings
                    )
                }
            }

            RoundLauncher(
                modifier = Modifier.fillMaxSize(),
                radiusX = settings.rlWidth.dp,
                radiusY = settings.rlHeight.dp,
                viewModel = viewModel,
                onTap = {
                    scope.launch {
                        sheetState.show()
                    }
                },
                onDoubleTap = {},
                onPress = {},
                onLongPress = {},
                xOffset = settings.xOffset.dp,
                yOffset = settings.yOffset.dp
            )

        }

        composable(Screens.Settings.route) {
            SettingsPage {
                settings = it.applySettings(c)
                navController.popBackStack()
            }
        }

    }

}