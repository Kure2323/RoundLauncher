package com.polete.roundlauncher.ui.drawpage

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.navigation.Screens
import com.polete.roundlauncher.ui.components.AppIcon


@Composable
fun DrawerPage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    ) {

    // Data for getting and showing icons
    val appList by viewModel.appList.collectAsStateWithLifecycle()
    val iconList by viewModel.iconList.collectAsStateWithLifecycle()

    // Just for not showing the touch animation
    val interactionSource = remember { MutableInteractionSource() }

    var searchText by remember {
        mutableStateOf("")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(interactionSource = interactionSource, indication = null) {
                navController.navigate(Screens.HomePage.route)
            }
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp
            )
    ) {

        Column(
            modifier = modifier.fillMaxSize()
        ) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {

                Box(
                    modifier = modifier
                        .size(56.dp)
                        .background(Color.Black.copy(0.2f))
                        .clickable { navController.navigate(Screens.Settings.route) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = modifier.size(28.dp),
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings Button"
                    )
                }
                Spacer(modifier.size(28.dp))

                Box(
                    modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(0.2f))
                ) {

                    /*** SearchBar ***/
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = modifier.fillMaxSize()
                    )

                }

            }

            Spacer(modifier.size(28.dp))

            Box(
                modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(0.2f))
            ) {
                /*** Cajón de aplicaciones ***/
                AppGrid(
                    searchText = searchText.trim().lowercase(),
                    onAppClick = { app ->
                        viewModel.launchUApp(app)
                        navController.navigate(Screens.HomePage.route)
                    },
                    appList = appList,
                    iconList = iconList
                )

            }


        }

    }

}

@Composable
fun AppGrid(
    modifier: Modifier = Modifier,
    searchText: String,
    onAppClick: (UApp) -> Unit,
    appList: List<UApp>,
    iconList: Map<String, Bitmap>
) {

    val filteredList = remember(searchText, appList) {
        appList.filter {
            it.label.lowercase().contains(searchText)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(5), // 4 columnas
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(filteredList, key = { "${it.packageName}-${it.user.hashCode()}" }) { app ->
            val key = "${app.packageName}-${app.user.hashCode()}"

            val icon = iconList[key]

            icon?.let {
                AppIcon(
                    app = app,
                    bitmap = it,
                    onClick = { onAppClick(app) }
                )
            }

        }
    }

}