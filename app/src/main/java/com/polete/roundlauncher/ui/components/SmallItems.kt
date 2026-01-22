package com.polete.roundlauncher.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.system.drawableToBitmap
import com.polete.roundlauncher.system.icon.IconRepository

@Composable //TODO hacerlo pero bien, que solo muestra el fondo sin colorines y es to feo
fun AppIcon(
    modifier: Modifier = Modifier,
    app: UApp,
    ) {

    val repo = IconRepository(LocalContext.current)
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(Unit) {
        bitmap = drawableToBitmap(repo.getIcon(app))
    }


    bitmap?.let {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = app.label,
            modifier = modifier.size(48.dp),
            contentScale = ContentScale.Fit
        )
    }
}