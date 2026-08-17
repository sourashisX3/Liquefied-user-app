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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import kotlin.math.cos
import kotlin.math.sin

data class DiamondConfig(
    val centerFraction: Offset,
    val sizeDp: Float,
    val alpha: Float,
    val rotationDurationMs: Int,
    val scaleDurationMs: Int,
    val delayMs: Int,
)

private val defaultAuthDiamonds = listOf(
    DiamondConfig(Offset(0.08f, 0.06f), 60f, 0.10f, 5500, 2500, 0),
    DiamondConfig(Offset(0.88f, 0.12f), 75f, 0.05f, 7500, 3000, 350),
    DiamondConfig(Offset(0.50f, 0.08f), 65f, 0.08f, 6500, 2800, 150),
    DiamondConfig(Offset(0.22f, 0.42f), 55f, 0.12f, 8000, 3200, 500),
    DiamondConfig(Offset(0.78f, 0.36f), 70f, 0.06f, 6000, 2600, 250),
)

val brandHeaderDiamonds = listOf(
    DiamondConfig(Offset(0.92f, 0.08f), 72f, 0.20f, 5500, 2600, 0),
    DiamondConfig(Offset(0.80f, 0.22f), 56f, 0.16f, 7200, 3200, 250),
    DiamondConfig(Offset(0.97f, 0.28f), 44f, 0.14f, 6400, 2800, 120),
    DiamondConfig(Offset(0.68f, 0.10f), 40f, 0.15f, 8000, 3600, 450),
    DiamondConfig(Offset(0.86f, 0.44f), 36f, 0.13f, 5800, 3000, 300),
    DiamondConfig(Offset(0.60f, 0.32f), 34f, 0.12f, 9000, 4000, 600),
)

private const val MAX_DIAMOND_SCALE = 1.3f
private const val GOLDEN_ANGLE = 2.399963f
private const val MAX_PLACEMENT_ATTEMPTS = 80

private fun resolvePlacements(diamonds: List<DiamondConfig>, width: Float, height: Float, density: Float): List<Offset> {
    val ordered = diamonds.sortedByDescending { it.sizeDp }
    val placements = mutableListOf<Offset>()
    val radii = mutableListOf<Float>()

    ordered.forEach { diamond ->
        val maxRadius = diamond.sizeDp * density * MAX_DIAMOND_SCALE * 0.5f
        val desired = Offset(diamond.centerFraction.x * width, diamond.centerFraction.y * height)
        val isFree: (Offset) -> Boolean = { candidate ->
            radii.indices.none { i ->
                val dx = candidate.x - placements[i].x
                val dy = candidate.y - placements[i].y
                dx * dx + dy * dy < (maxRadius + radii[i]) * (maxRadius + radii[i])
            }
        }

        var placement = desired
        if (!isFree(desired)) {
            var attempt = 0
            var angle = 0f
            var distance = 0f
            while (attempt < MAX_PLACEMENT_ATTEMPTS) {
                attempt++
                angle += GOLDEN_ANGLE
                distance += maxRadius * 0.35f
                val candidate = Offset(
                    x = (desired.x + cos(angle) * distance).coerceIn(maxRadius, width - maxRadius),
                    y = (desired.y + sin(angle) * distance).coerceIn(maxRadius, height - maxRadius),
                )
                if (isFree(candidate)) {
                    placement = candidate
                    break
                }
            }
        }
        placements.add(placement)
        radii.add(maxRadius)
    }

    return placements
}

@Composable
fun AnimatedDiamonds(
    modifier: Modifier = Modifier,
    baseColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    diamonds: List<DiamondConfig> = defaultAuthDiamonds,
) {
    val infiniteTransition = rememberInfiniteTransition()

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
            targetValue = MAX_DIAMOND_SCALE,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = diamond.scaleDurationMs, delayMillis = diamond.delayMs),
                repeatMode = RepeatMode.Reverse,
            ),
        )
    }

    val placements = remember(diamonds) {
        resolvePlacements(diamonds, width = 360f, height = 200f, density = 1f)
    }

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val radiansPerDegree = 0.0174533f

        diamonds.forEachIndexed { index, diamond ->
            val halfSize = diamond.sizeDp * density * scales[index].value * 0.5f
            val angle = rotations[index].value * radiansPerDegree
            val cx = placements[index].x / 360f * w
            val cy = placements[index].y / 200f * h

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