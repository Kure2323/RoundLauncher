package com.polete.roundlauncher.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.polete.roundlauncher.MainViewModel

@Composable
fun WallpaperBox(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    content: @Composable () -> Unit
) {

    val wallpaper by viewModel.wallpaper.collectAsStateWithLifecycle()

    Box(
        modifier.fillMaxSize(),
    ) {
        wallpaper?.asImageBitmap()?.let {
            Image(
                bitmap = it,
                contentDescription = "Wallpaper",
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier.fillMaxSize().systemBarsPadding()
        ) {
            content()
        }

    }

}