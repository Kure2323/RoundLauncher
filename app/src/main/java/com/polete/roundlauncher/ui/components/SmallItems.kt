package com.polete.roundlauncher.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.data.UApp

@Composable
fun AppIcon(
    app: UApp,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onClick: (UApp) -> Unit
) {

    val appicon by viewModel.getIcon(app).collectAsStateWithLifecycle()

    appicon?.let {
        Column(
            modifier = modifier.fillMaxSize()
                .clickable {
                    onClick(app)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                bitmap = appicon!!,
                contentDescription = app.label,
                modifier = modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = app.label,
                textAlign = TextAlign.Center,
                maxLines = 2,
                softWrap = true,
                style = MaterialTheme.typography.labelSmall,
                modifier = modifier.fillMaxWidth().height(28.dp)
            )
        }
    }
}


