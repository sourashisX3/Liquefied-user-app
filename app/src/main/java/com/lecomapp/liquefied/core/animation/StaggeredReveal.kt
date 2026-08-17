package com.lecomapp.liquefied.core.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.lecomapp.liquefied.core.ui.theme.AnimationTokens
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Visual spec for a staggered reveal animation.
 *
 * @param duration per-section animation duration in ms.
 * @param staggerDelay delay between consecutive sections in ms.
 * @param startOffsetY starting vertical offset (fraction of the section height).
 * @param offsetSpring when set, the vertical offset uses a spring instead of a tween.
 */
data class RevealSpec(
    val duration: Int = AnimationTokens.Duration.Medium,
    val staggerDelay: Int = AnimationTokens.StaggerDelay,
    val startOffsetY: Float = 1f,
    val offsetSpring: SpringSpec<Float>? = null,
)

/**
 * A single reveal animation: fade-in alpha plus optional slide-up offset.
 */
class RevealAnim internal constructor(
    val alpha: Animatable<Float, AnimationVector1D>,
    val offsetY: Animatable<Float, AnimationVector1D>,
)

/**
 * Creates [count] reveal animations and plays them sequentially with a stagger.
 * [specFor] allows a per-index spec (e.g. a spring for the first section).
 * The returned list is stable across recompositions; animations run once per [count].
 */
@Composable
fun rememberStaggeredReveals(
    count: Int,
    specFor: (Int) -> RevealSpec = { RevealSpec() },
): List<RevealAnim> {
    val anims = remember(count) {
        List(count) {
            val spec = specFor(it)
            RevealAnim(
                alpha = Animatable(0f),
                offsetY = Animatable(spec.startOffsetY),
            )
        }
    }

    LaunchedEffect(count) {
        anims.forEachIndexed { index, anim ->
            launch {
                delay(specFor(index).staggerDelay * index.toLong())
                anim.play(specFor(index))
            }
        }
    }

    return anims
}

private suspend fun RevealAnim.play(spec: RevealSpec) {
    coroutineScope {
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = spec.duration,
                    easing = FastOutSlowInEasing,
                ),
            )
        }
        launch {
            val springSpec = spec.offsetSpring
            if (springSpec != null) {
                offsetY.animateTo(targetValue = 0f, animationSpec = springSpec)
            } else {
                offsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = spec.duration,
                        easing = FastOutSlowInEasing,
                    ),
                )
            }
        }
    }
}