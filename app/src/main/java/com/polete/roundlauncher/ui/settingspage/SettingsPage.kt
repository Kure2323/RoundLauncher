package com.polete.roundlauncher.ui.settingspage

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.polete.roundlauncher.Container
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.R
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.system.getKey
import com.polete.roundlauncher.ui.components.AppIcon

@Composable
fun SettingsBack(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
    ) {

    Scaffold(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
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
        contentAlignment = Alignment.Center,

    ) {
        Box(
            modifier.padding(8.dp)
        ) {
            content()
        }
    }

}

@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    onApply: (SettingsK) -> Unit
) {
    val allApps by viewModel.appList.collectAsStateWithLifecycle()
    val _dbApps by viewModel.dbList.collectAsStateWithLifecycle()
    val icons by viewModel.iconList.collectAsStateWithLifecycle()

    val settings = Container.settings.copy()

    var heightText by rememberSaveable { mutableStateOf(settings.rlHeight.toString()) }
    var widthText by rememberSaveable { mutableStateOf(settings.rlWidth.toString()) }
    var isSorted by rememberSaveable { mutableStateOf(settings.drIsSorted) }
    var isInstant by rememberSaveable { mutableStateOf(settings.sbIsInstant) }
    var xOffset by rememberSaveable { mutableStateOf(settings.xOffset.toString()) }
    var yOffset by rememberSaveable { mutableStateOf(settings.yOffset.toString()) }

    Box(modifier = Modifier.fillMaxSize()) {

        SettingsBack {
            Column {

                // App selection
                LazyColumn(modifier.fillMaxSize()) {

                    item {
                        // Height
                        SettingsCard {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.height),
                                    modifier.weight(2f),
                                    textAlign = TextAlign.Center
                                )
                                OutlinedTextField(
                                    value = heightText,
                                    onValueChange = {
                                        heightText = it
                                        it.toIntOrNull()?.let { num ->
                                            settings.rlHeight = num
                                        }
                                    },
                                    modifier = modifier
                                        .fillMaxSize()
                                        .weight(4f),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )

                                )
                            }
                        }
                    }

                    item {

                        // Width
                        SettingsCard {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.width),
                                    modifier.weight(2f),
                                    textAlign = TextAlign.Center
                                )
                                OutlinedTextField(
                                    value = widthText,
                                    onValueChange = {
                                        widthText = it
                                        it.toIntOrNull()?.let { num ->
                                            settings.rlWidth = num
                                        }
                                    },
                                    modifier = modifier
                                        .fillMaxSize()
                                        .weight(4f),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )
                                )
                            }
                        }
                    }

                    item {

                        // X
                        SettingsCard {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.xOffset),
                                    modifier.weight(2f),
                                    textAlign = TextAlign.Center
                                )
                                OutlinedTextField(
                                    value = xOffset,
                                    onValueChange = {
                                        xOffset = it
                                        it.toIntOrNull()?.let { num ->
                                            settings.xOffset = num
                                        }
                                    },
                                    modifier = modifier
                                        .fillMaxSize()
                                        .weight(4f),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )
                                )
                            }
                        }
                    }

                    item {

                        // Y
                        SettingsCard {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.yOffset),
                                    modifier.weight(2f),
                                    textAlign = TextAlign.Center
                                )
                                OutlinedTextField(
                                    value = yOffset,
                                    onValueChange = {
                                        yOffset = it
                                        it.toIntOrNull()?.let { num ->
                                            settings.yOffset = num
                                        }
                                    },
                                    modifier = modifier
                                        .fillMaxSize()
                                        .weight(4f),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )
                                )
                            }
                        }
                    }

                    item {

                        // IsSorted
                        SettingsCard {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.isSorted),
                                    modifier.weight(2f),
                                    textAlign = TextAlign.Center
                                )
                                Checkbox(
                                    checked = isSorted,
                                    onCheckedChange = {
                                        isSorted = it
                                    }
                                )
                            }
                        }
                    }

                    item {
                        // IsInstant
                        SettingsCard {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(R.string.isInstant),
                                    modifier.weight(2f),
                                    textAlign = TextAlign.Center
                                )
                                Checkbox(
                                    checked = isInstant,
                                    onCheckedChange = {
                                        isInstant = it
                                    }
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                        )
                    }

                    item {
                        Text(
                            text = "App Selection",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = modifier.fillMaxWidth()
                        )
                    }

                    item {
                        Spacer(modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                        )
                    }

                    items(items = allApps) {
                        AppCard(
                            app = it,
                            bitmap = icons[getKey(it)],
                            list = _dbApps
                        )
                    }
                    item {
                        Spacer(modifier.height(48.dp))
                    }
                }

            }
            Button(
                onClick = {
                    settings.drIsSorted = isSorted
                    settings.sbIsInstant = isInstant
                    onApply(settings)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("Apply")
            }
        }


    }
}

@Composable
fun AppCard(
    viewModel: MainViewModel = viewModel(),
    app: UApp,
    bitmap: Bitmap?, list: List<String>
) {

    var isOn by rememberSaveable {
        mutableStateOf(list.contains(getKey(app)))
    }

    SettingsCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            AppIcon(
                app = app,
                bitmap = bitmap
            ) {/* No queremos que haga nada */}
            Text(app.label)
            Checkbox(
                checked = isOn,
                onCheckedChange = {
                    isOn = viewModel.appCheckBoxAction(app)

                }
            )
        }
    }

}