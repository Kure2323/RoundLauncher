package com.polete.roundlauncher.ui.settingspage

import android.content.res.Configuration
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polete.roundlauncher.R

@Composable
fun SettingsPage(
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
fun RoundSettings(
    modifier: Modifier = Modifier,

    ) {
    SettingsPage() {
        Column {
            Row(
                modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Text(stringResource(R.string.height))

            }
        }
    }
}