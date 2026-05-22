package com.polete.roundlauncher.ui.homepage

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.polete.roundlauncher.MainViewModel
import com.polete.roundlauncher.system.getKey
import com.polete.roundlauncher.ui.components.AppIcon
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun RoundLauncher(
    modifier: Modifier = Modifier,
    radiusX: Dp,
    radiusY: Dp,
    viewModel: MainViewModel = viewModel(),
    onTap: () -> Unit,
    onDoubleTap: () -> Unit,
    onPress: () -> Unit,
    onLongPress: () -> Unit,
    boxModifier: Modifier = Modifier,
    xOffset: Dp,
    yOffset: Dp
) {

    // It is going to determinate the rotation in the wheel
    // Will be modified by gestures
    var rotation by remember { mutableFloatStateOf(0f) }

    val _appList by viewModel.appList.collectAsStateWithLifecycle()
    val dbList by viewModel.dbList.collectAsStateWithLifecycle()

    val appList = if (dbList.isEmpty()) {
        try {
            _appList.take(10)
        } catch (_: Exception) {
            _appList
        }
    } else {
        _appList.filter { app ->
            dbList.contains(getKey(app))
        }
    }



    val iconList by viewModel.iconList.collectAsStateWithLifecycle()

    val density = LocalDensity.current
    val radiusXPx = with(density) { radiusX.toPx() }
    val radiusYPx = with(density) { radiusY.toPx() }

    Box(
        modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { onDoubleTap() },
                    onLongPress = { onLongPress() },
                    onPress = { onPress() },
                    onTap = { onTap() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = boxModifier
                .offset(x = xOffset, y = yOffset)
                .size(radiusX * 2)
                .pointerInput(radiusXPx, radiusYPx) {

                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)

                        val center = Offset(size.width / 2f, size.height / 2f)

                        var previousAngle = atan2(
                            (down.position.y - center.y) / radiusYPx,
                            (down.position.x - center.x) / radiusXPx
                        )

                        do {
                            // Gets the last pointer event: press, tap...
                            val event = awaitPointerEvent()
                            val change = event.changes.first()

                            val currentAngle = atan2(
                                (change.position.y - center.y) / radiusYPx,
                                (change.position.x - center.x) / radiusXPx
                            )

                            var delta = currentAngle - previousAngle

                            if (delta > Math.PI) delta -= (2 * Math.PI).toFloat()
                            if (delta < -Math.PI) delta += (2 * Math.PI).toFloat()

                            rotation += Math.toDegrees(delta.toDouble()).toFloat()
                            previousAngle = currentAngle

                            change.consume()
                        } while (change.pressed)
                    }

                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { onDoubleTap() },
                        onLongPress = { onLongPress() },
                        onPress = { onPress() },
                        onTap = { onTap() }
                    )
                },
            contentAlignment = Alignment.Center
        ) {

            appList.forEachIndexed { index, app ->

                val angle = (360f / appList.size) * index + rotation
                val radians = Math.toRadians(angle.toDouble())

                val x = radiusXPx * cos(radians)
                val y = radiusYPx * sin(radians)

                val bitmap = iconList[getKey(app)]

                Box(boxModifier.offset {
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
}