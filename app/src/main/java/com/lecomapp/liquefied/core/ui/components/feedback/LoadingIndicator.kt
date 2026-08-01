package com.lecomapp.liquefied.core.ui.components.feedback

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    dotSize: Dp = 16.dp,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedDots(dotSize = dotSize, color = color)
    }
}

@Composable
fun LoadingRow(
    modifier: Modifier = Modifier,
    dotSize: Dp = 10.dp,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        AnimatedDots(dotSize = dotSize, color = color)
    }
}

@Composable
private fun AnimatedDots(
    dotSize: Dp,
    color: Color,
) {
    val transition = rememberInfiniteTransition(label = "loadingDots")
    val dotStates = List(3) { index ->
        transition.animateFloat(
            initialValue = 1f,
            targetValue = 0.35f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1050
                    1f at 0 using FastOutSlowInEasing
                    0.35f at 350 using FastOutSlowInEasing
                    1f at 1050 using FastOutSlowInEasing
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(index * 140),
            ),
            label = "loadingDot$index",
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(dotSize / 3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        dotStates.forEach { dot ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .graphicsLayer {
                        scaleX = dot.value
                        scaleY = dot.value
                        alpha = 0.3f + 0.7f * dot.value
                    }
                    .background(color = color, shape = CircleShape),
            )
        }
    }
}
