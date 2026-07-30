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
    val titleOffsetY: Animatable<Float, AnimationVector1D>,
    val titleAlpha: Animatable<Float, AnimationVector1D>,
    val formOffsetY: Animatable<Float, AnimationVector1D>,
    val formAlpha: Animatable<Float, AnimationVector1D>,
    val buttonOffsetY: Animatable<Float, AnimationVector1D>,
    val buttonAlpha: Animatable<Float, AnimationVector1D>,
)

@Composable
fun rememberLoginAnimState(): LoginAnimState {
    return remember {
        LoginAnimState(
            titleOffsetY = Animatable(0.3f),
            titleAlpha = Animatable(0f),
            formOffsetY = Animatable(0.2f),
            formAlpha = Animatable(0f),
            buttonOffsetY = Animatable(0.15f),
            buttonAlpha = Animatable(0f),
        )
    }
}

suspend fun LoginAnimState.animateSequence() {
    coroutineScope {
        launch {
            titleOffsetY.animateTo(
                targetValue = 0f,
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
            formOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            )
        }
        launch {
            formAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400),
            )
        }
    }

    coroutineScope {
        launch {
            buttonOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            )
        }
        launch {
            buttonAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400),
            )
        }
    }
}
