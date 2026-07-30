package com.lecomapp.liquefied.features.splash.presentation.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashAnimState(
    val logoScale: Animatable<Float, AnimationVector1D>,
    val logoAlpha: Animatable<Float, AnimationVector1D>,
    val taglineOffsetY: Animatable<Float, AnimationVector1D>,
    val taglineAlpha: Animatable<Float, AnimationVector1D>,
)

@Composable
fun rememberSplashAnimState(): SplashAnimState {
    return remember {
        SplashAnimState(
            logoScale = Animatable(0.6f),
            logoAlpha = Animatable(0f),
            taglineOffsetY = Animatable(0.4f),
            taglineAlpha = Animatable(0f),
        )
    }
}

suspend fun SplashAnimState.animateSequence(onFinished: suspend () -> Unit) {
    coroutineScope {
        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            )
        }
        launch {
            logoAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 700),
            )
        }
    }

    coroutineScope {
        launch {
            taglineOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            )
        }
        launch {
            taglineAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500),
            )
        }
    }

    delay(600)
    onFinished()
}
