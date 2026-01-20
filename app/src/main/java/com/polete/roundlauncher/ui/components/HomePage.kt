package com.polete.roundlauncher.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.R
import com.polete.roundlauncher.data.AppModule

@Composable
fun HomePage(
    viewModel: MainViewModel,
    onAppClick: (AppModule) -> Unit,
    modifier: Modifier = Modifier
) {

    val appList by viewModel.appList.collectAsStateWithLifecycle()

    if (appList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize().padding(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(R.string.loading_appList),
                textAlign = TextAlign.Center
            )
        }
    }

    LazyColumn(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        items(items = appList, key = {it.packageName}) { app ->

            Text(
                text = app.label,
                Modifier.clickable {onAppClick(app)}
            )

        }

    }

}