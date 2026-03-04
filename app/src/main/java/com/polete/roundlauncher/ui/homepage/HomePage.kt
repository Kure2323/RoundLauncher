package com.polete.roundlauncher.ui.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.polete.roundlauncher.MainViewModel

@Composable
fun HomePage(
    onBackgroundClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
    ) {

    // Hace que no se vea la animación de feedback al pulsar en el fondo
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier
            .fillMaxSize()
            .clickable(indication = null, interactionSource = interactionSource) {
                onBackgroundClick()
            }
    ) {

    }

}