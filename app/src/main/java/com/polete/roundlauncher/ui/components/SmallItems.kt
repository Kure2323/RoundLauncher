package com.polete.roundlauncher.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.polete.roundlauncher.data.UApp

@Composable
fun AppIcon(
    app: UApp,
    bitmap: Bitmap?,
    modifier: Modifier = Modifier,
    onClick: (UApp) -> Unit
) {

    Box(
        modifier = modifier
            .size(48.dp)
            .clickable { onClick(app) }
            .background(androidx.compose.ui.graphics.Color.Gray.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = app.label,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }

}


