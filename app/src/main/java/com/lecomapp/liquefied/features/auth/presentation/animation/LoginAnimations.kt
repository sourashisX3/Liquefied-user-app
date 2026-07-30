package com.lecomapp.liquefied.features.auth.presentation.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoginAnimState(
    val titleScale: Animatable<Float, AnimationVector1D>,
    val titleAlpha: Animatable<Float, AnimationVector1D>,
    val cardOffsetY: Animatable<Float, AnimationVector1D>,
    val cardAlpha: Animatable<Float, AnimationVector1D>,
    val headerAlpha: Animatable<Float, AnimationVector1D>,
    val formAlpha: Animatable<Float, AnimationVector1D>,
    val buttonAlpha: Animatable<Float, AnimationVector1D>,
)

@Composable
fun rememberLoginAnimState(): LoginAnimState {
    return remember {
        LoginAnimState(
            titleScale = Animatable(0.6f),
            titleAlpha = Animatable(0f),
            cardOffsetY = Animatable(0.5f),
            cardAlpha = Animatable(0f),
            headerAlpha = Animatable(0f),
            formAlpha = Animatable(0f),
            buttonAlpha = Animatable(0f),
        )
    }
}

suspend fun LoginAnimState.animateSequence() {
    coroutineScope {
        launch {
            titleScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            )
        }
        launch {
            titleAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500),
            )
        }
    }

    coroutineScope {
        launch {
            cardOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            )
        }
        launch {
            cardAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500),
            )
        }
    }

    coroutineScope {
        launch {
            headerAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400),
            )
        }
    }

    coroutineScope {
        launch {
            formAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400),
            )
        }
    }

    coroutineScope {
        launch {
            buttonAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400),
            )
        }
    }
}
