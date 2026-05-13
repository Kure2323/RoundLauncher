package com.polete.roundlauncher.ui.settingspage

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.polete.roundlauncher.Container
import com.polete.roundlauncher.R

@Composable
fun SettingsBack(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
    ) {

    var isOpen by remember { mutableStateOf(false) }

    val offsetX by animateDpAsState(
        targetValue = if (isOpen) 0.dp else (-300).dp,
        label = "slide"
    )

    Scaffold(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentWindowInsets = WindowInsets(),
        topBar = {
            Text(
                text = stringResource(R.string.settings),
                modifier = modifier
                    .windowInsetsPadding(insets = WindowInsets.statusBars)
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(12.dp)
                .clip(shape = MaterialTheme.shapes.large)
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
            ) {

            content()

        }

    }

}

@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Box(
        modifier
            .padding(8.dp)
            .height(80.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow),
        contentAlignment = Alignment.Center
    ) {

        content()

    }

}

@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
    onApply: (SettingsK) -> Unit
) {
    val settings = remember {
        Container.settings.copy()
    }
    var heightText by rememberSaveable { mutableStateOf(settings.rlHeight.toString()) }
    var widthText by rememberSaveable { mutableStateOf(settings.rlWidth.toString()) }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            SettingsBack() {
                Column {

                    SettingsCard() {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(stringResource(R.string.height))
                            TextField(
                                value = heightText,
                                onValueChange = {
                                    heightText = it
                                    it.toIntOrNull()?.let { num ->
                                        settings.rlHeight = num
                                    }
                                }
                            )
                        }
                    }

                    SettingsCard() {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(stringResource(R.string.width))
                            TextField(
                                value = widthText,
                                onValueChange = {
                                    widthText = it
                                    it.toIntOrNull()?.let { num ->
                                        settings.rlWidth = num
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = { onApply(settings) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Apply")
        }
    }
}