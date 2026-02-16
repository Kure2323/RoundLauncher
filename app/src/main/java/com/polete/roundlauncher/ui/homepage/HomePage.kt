package com.polete.roundlauncher.ui.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.navigation.Screens

@Composable
fun HomePage(
    navController: NavHostController,
    onAppClick: (UApp) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
    ) {

    Box(
        modifier
            .fillMaxSize()
            .clickable {
                navController.navigate(Screens.DrawPage.route)
            }
    ) {

    }

}