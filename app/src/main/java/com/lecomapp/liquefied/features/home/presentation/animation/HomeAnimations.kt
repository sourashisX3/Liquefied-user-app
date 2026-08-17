package com.lecomapp.liquefied.features.home.presentation.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeAnimState(
    val headerAlpha: Animatable<Float, AnimationVector1D>,
    val headerOffsetY: Animatable<Float, AnimationVector1D>,
    val bannerAlpha: Animatable<Float, AnimationVector1D>,
    val bannerOffsetY: Animatable<Float, AnimationVector1D>,
    val categoriesAlpha: Animatable<Float, AnimationVector1D>,
    val categoriesOffsetY: Animatable<Float, AnimationVector1D>,
    val brandsAlpha: Animatable<Float, AnimationVector1D>,
    val brandsOffsetY: Animatable<Float, AnimationVector1D>,
    val railsAlpha: Animatable<Float, AnimationVector1D>,
    val railsOffsetY: Animatable<Float, AnimationVector1D>,
)

@Composable
fun rememberHomeAnimState(): HomeAnimState {
    return remember {
        HomeAnimState(
            headerAlpha = Animatable(0f),
            headerOffsetY = Animatable(1f),
            bannerAlpha = Animatable(0f),
            bannerOffsetY = Animatable(1f),
            categoriesAlpha = Animatable(0f),
            categoriesOffsetY = Animatable(1f),
            brandsAlpha = Animatable(0f),
            brandsOffsetY = Animatable(1f),
            railsAlpha = Animatable(0f),
            railsOffsetY = Animatable(1f),
        )
    }
}

suspend fun HomeAnimState.animateSequence() {
    coroutineScope {
        launch {
            headerAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 600),
            )
        }
        launch {
            headerOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium,
                ),
            )
        }
    }

    coroutineScope {
        launch {
            bannerAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
            )
        }
        launch {
            bannerOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
            )
        }
    }

    coroutineScope {
        launch {
            categoriesAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
            )
        }
        launch {
            categoriesOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
            )
        }
    }

    coroutineScope {
        launch {
            brandsAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
            )
        }
        launch {
            brandsOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
            )
        }
    }

    coroutineScope {
        launch {
            railsAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
            )
        }
        launch {
            railsOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
            )
        }
    }
}
