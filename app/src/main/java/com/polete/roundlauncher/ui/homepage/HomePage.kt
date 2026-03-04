package com.polete.roundlauncher.ui.homepage

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.ui.components.AppIcon
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun HomePage(
    onHomeInteraction: Modifier.() -> Modifier,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
    ) {

    // Hace que no se vea la animación de feedback al pulsar en el fondo
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier
            .fillMaxSize()
            .onHomeInteraction(),
        contentAlignment = Alignment.Center
    ) {

        RoundLauncher(
            radiusX = 100.dp,
            radiusY = 100.dp,
            viewModel = viewModel,
            modifier = modifier
        )

    }

}

@Composable
fun RoundLauncher(
    radiusX: Dp,
    radiusY: Dp,
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    // It is going to determinate the rotation in the wheel
    // Will be modified by gestures
    var rotation by remember { mutableFloatStateOf(0f) }

    val _appList by viewModel.appList.collectAsStateWithLifecycle()
    val appList = _appList.filter {
        it.label.lowercase().contains("p")
    }


    val iconList by viewModel.iconList.collectAsStateWithLifecycle()

    val density = LocalDensity.current
    val radiusXPx = with(density) { radiusX.toPx() }
    val radiusYPx = with(density) { radiusY.toPx() }

    Box(
        modifier = modifier
            .size(radiusX * 2)
            .pointerInput(radiusXPx, radiusYPx) {
                awaitEachGesture {

                    val down = awaitFirstDown()

                    val center = Offset(
                        size.width / 2f,
                        size.height / 2f
                    )

                    var previousAngle = atan2(
                        (down.position.y - center.y) / radiusYPx,
                        (down.position.x - center.x) / radiusXPx
                    )

                    do {
                        val event = awaitPointerEvent()
                        val change = event.changes.first()

                        val currentAngle = atan2(
                            (change.position.y - center.y) / radiusYPx,
                            (change.position.x - center.x) / radiusXPx
                        )

                        var delta = currentAngle - previousAngle

                        // Corrección salto ±π
                        if (delta > Math.PI) delta -= (2 * Math.PI).toFloat()
                        if (delta < -Math.PI) delta += (2 * Math.PI).toFloat()

                        rotation += Math.toDegrees(delta.toDouble()).toFloat()

                        previousAngle = currentAngle

                        change.consume()

                    } while (change.pressed)
                }
            },
        contentAlignment = Alignment.Center
    ) {

        appList.forEachIndexed { index, app ->

            val angle = (360f / appList.size) * index + rotation
            val radians = Math.toRadians(angle.toDouble())

            val x = radiusXPx * cos(radians)
            val y = radiusYPx * sin(radians)

            val bitmap = iconList["${app.packageName}-${app.user.hashCode()}"]

            Box(modifier.offset {
                IntOffset(x.roundToInt(), y.roundToInt())
            }) {
                AppIcon(
                    app = app,
                    bitmap = bitmap,
                    onClick = {
                        viewModel.launchUApp(app)
                    }
                )

            }
        }
    }
}