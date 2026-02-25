package com.polete.roundlauncher.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
    bitmap: Bitmap,
    modifier: Modifier = Modifier,
    onClick: (UApp) -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize()
            .clickable {
                onClick(app)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = app.label,
            modifier = modifier.size(48.dp),
            contentScale = ContentScale.Fit
        )
//            Text(
//                text = app.label,
//                textAlign = TextAlign.Center,
//                maxLines = 2,
//                softWrap = true,
//                style = MaterialTheme.typography.labelSmall,
//                modifier = modifier.fillMaxWidth().height(28.dp)
//            )
    }

}


