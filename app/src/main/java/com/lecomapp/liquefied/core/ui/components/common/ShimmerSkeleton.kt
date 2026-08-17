package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.lecomapp.liquefied.core.ui.theme.LiquefiedColors

@Composable
fun ShimmerSkeleton(
    modifier: Modifier = Modifier,
    shape: Shape,
    baseColor: Color = LiquefiedColors.Utility.skeleton,
    highlightColor: Color = Color.White.copy(alpha = 0.45f),
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
        ),
        label = "shimmerProgress",
    )
    Box(
        modifier = modifier
            .clip(shape)
            .background(baseColor)
            .drawBehind {
                val bandWidth = size.width * 0.4f
                val startX = -bandWidth + progress * (size.width + 2 * bandWidth)
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Transparent, highlightColor, Color.Transparent),
                        start = Offset(startX - bandWidth, 0f),
                        end = Offset(startX + bandWidth, size.height),
                    ),
                )
            },
    )
}