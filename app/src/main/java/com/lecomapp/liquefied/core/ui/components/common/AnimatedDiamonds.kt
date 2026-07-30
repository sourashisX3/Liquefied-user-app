package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import kotlin.math.cos
import kotlin.math.sin

private data class DiamondConfig(
    val centerFraction: Offset,
    val sizeDp: Float,
    val alpha: Float,
    val rotationDurationMs: Int,
    val scaleDurationMs: Int,
    val delayMs: Int,
)

@Composable
fun AnimatedDiamonds(modifier: Modifier = Modifier) {
    val baseColor = MaterialTheme.colorScheme.secondaryContainer
    val infiniteTransition = rememberInfiniteTransition()

    val diamonds = remember {
        listOf(
            DiamondConfig(Offset(0.08f, 0.06f), 60f, 0.10f, 5500, 2500, 0),
            DiamondConfig(Offset(0.88f, 0.12f), 75f, 0.05f, 7500, 3000, 350),
            DiamondConfig(Offset(0.50f, 0.08f), 65f, 0.08f, 6500, 2800, 150),
            DiamondConfig(Offset(0.22f, 0.42f), 55f, 0.12f, 8000, 3200, 500),
            DiamondConfig(Offset(0.78f, 0.36f), 70f, 0.06f, 6000, 2600, 250),
        )
    }

    val rotations = diamonds.map { diamond ->
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = diamond.rotationDurationMs, delayMillis = diamond.delayMs),
                repeatMode = RepeatMode.Restart,
            ),
        )
    }

    val scales = diamonds.map { diamond ->
        infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = diamond.scaleDurationMs, delayMillis = diamond.delayMs),
                repeatMode = RepeatMode.Reverse,
            ),
        )
    }

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val radiansPerDegree = 0.0174533f

        diamonds.forEachIndexed { index, diamond ->
            val cx = diamond.centerFraction.x * w
            val cy = diamond.centerFraction.y * h
            val halfSize = diamond.sizeDp * density * scales[index].value * 0.5f
            val angle = rotations[index].value * radiansPerDegree

            val path = Path().apply {
                val p1x = cx + halfSize * cos(angle)
                val p1y = cy + halfSize * sin(angle)
                val p2x = cx - halfSize * sin(angle)
                val p2y = cy + halfSize * cos(angle)
                val p3x = cx - halfSize * cos(angle)
                val p3y = cy - halfSize * sin(angle)
                val p4x = cx + halfSize * sin(angle)
                val p4y = cy - halfSize * cos(angle)

                moveTo(p1x, p1y)
                lineTo(p2x, p2y)
                lineTo(p3x, p3y)
                lineTo(p4x, p4y)
                close()
            }

            drawPath(path, color = baseColor.copy(alpha = diamond.alpha))
        }
    }
}
