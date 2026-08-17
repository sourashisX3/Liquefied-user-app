package com.lecomapp.liquefied.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Composable
fun rememberImagePlaceholderColor(): Color = LocalSkeletonColors.current.surfacePlaceholder

data class SkeletonColors(
    val base: Color,
    val highlight: Color,
    val surfacePlaceholder: Color,
)

val LocalSkeletonColors = compositionLocalOf {
    SkeletonColors(
        base = Color.Unspecified,
        highlight = Color.Unspecified,
        surfacePlaceholder = Color.Unspecified,
    )
}

val LightSkeletonColors = SkeletonColors(
    base = LiquefiedColors.Skeleton.base,
    highlight = LiquefiedColors.Skeleton.highlight,
    surfacePlaceholder = LiquefiedColors.Neutral.surfacePlaceholder,
)

val DarkSkeletonColors = SkeletonColors(
    base = LiquefiedColors.SkeletonDark.base,
    highlight = LiquefiedColors.SkeletonDark.highlight,
    surfacePlaceholder = LiquefiedColors.NeutralDark.surfacePlaceholder,
)
