package com.polete.roundlauncher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.data.UApp


@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    searchBar: @Composable () -> Unit,
    onAppClick: (UApp) -> Unit,
    onSettingsClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
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
                        .clickable { onSettingsClick() },
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
                ) { searchBar() }

            }

            Spacer(modifier.size(28.dp))

            Box(
                modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(0.2f))
            ) {
                AppGrid(
                    viewModel = viewModel
                ) { app -> onAppClick(app) }
            }


        }

    }

}

@Composable
fun AppGrid(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MainViewModel,
    onAppClick: (UApp) -> Unit
    ) {

    val appList by viewModel.apps.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(4), // 4 columnas
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(appList, key = { it.packageName + it.user.hashCode() }) { app ->
            AppIcon(app = app, viewModel = viewModel) {
                onAppClick(app)
            }
        }
    }
    
}