package com.lecomapp.liquefied.core.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class ImageOverlayColors(
    val scrim: Color,
    val onScrim: Color,
)

val LocalImageOverlayColors = compositionLocalOf {
    ImageOverlayColors(
        scrim = Color.Unspecified,
        onScrim = Color.Unspecified,
    )
}

val LightImageOverlayColors = ImageOverlayColors(
    scrim = LiquefiedColors.Utility.black.copy(alpha = 0.30f),
    onScrim = LiquefiedColors.Utility.white,
)

val DarkImageOverlayColors = ImageOverlayColors(
    scrim = LiquefiedColors.Utility.black.copy(alpha = 0.40f),
    onScrim = LiquefiedColors.Utility.white,
)