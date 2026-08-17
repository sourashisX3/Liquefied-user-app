package com.lecomapp.liquefied.core.ui.theme

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Spacing {
    val xxxs: Dp = 2.dp
    val xxs: Dp = 4.dp
    val xs: Dp = 8.dp
    val sm: Dp = 12.dp
    val md: Dp = 16.dp
    val lg: Dp = 20.dp
    val xl: Dp = 24.dp
    val xxl: Dp = 32.dp
    val xxxl: Dp = 40.dp
    val huge: Dp = 48.dp
    val xhuge: Dp = 56.dp
    val xxhuge: Dp = 64.dp
}

object AppSpacing {
    val xxxs: Dp = 2.dp
    val xxs: Dp = 4.dp
    val xs: Dp = 8.dp
    val sm: Dp = 12.dp
    val md: Dp = 16.dp
    val lg: Dp = 20.dp
    val xl: Dp = 24.dp
    val xxl: Dp = 32.dp
    val xxxl: Dp = 40.dp
    val huge: Dp = 48.dp
    val xhuge: Dp = 56.dp
    val xxhuge: Dp = 64.dp
}

object AppCornerRadius {
    val extraSmall: Dp = 4.dp
    val small: Dp = 8.dp
    val medium: Dp = 12.dp
    val large: Dp = 16.dp
    val extraLarge: Dp = 20.dp
    val full: Dp = 30.dp
    val huge: Dp = 48.dp
}

object AppElevation {
    val none: Dp = 0.dp
    val xxs: Dp = 1.dp
    val xs: Dp = 2.dp
    val sm: Dp = 3.dp
    val md: Dp = 4.dp
    val lg: Dp = 6.dp
    val xl: Dp = 8.dp
    val xxl: Dp = 12.dp
    val xxxl: Dp = 16.dp
    val huge: Dp = 24.dp
}

object AppIconSize {
    val small: Dp = 16.dp
    val medium: Dp = 24.dp
    val large: Dp = 32.dp
    val extraLarge: Dp = 40.dp
    val navItem: Dp = 22.dp
    val headerAction: Dp = 28.dp
    val cardAction: Dp = 20.dp
}

object AppButton {
    val height: Dp = 48.dp
    val cornerRadius: Dp = 30.dp
    val minWidth: Dp = 64.dp
    val iconSize: Dp = 18.dp
}

object ComponentSize {
    val buttonHeightSmall: Dp = 40.dp
    val buttonHeightMedium: Dp = 48.dp
    val buttonHeightLarge: Dp = 56.dp
    val iconSmall: Dp = 16.dp
    val iconMedium: Dp = 24.dp
    val iconLarge: Dp = 32.dp
    val avatarSmall: Dp = 32.dp
    val avatarMedium: Dp = 48.dp
    val avatarLarge: Dp = 64.dp
    val productCardHeight: Dp = 280.dp
    val carouselHeight: Dp = 200.dp
}

data class DimensionTokens(
    val spacing: SpacingTokens = SpacingTokens(),
    val componentSize: ComponentSize = ComponentSize,
    val cornerRadius: CornerRadiusTokens = CornerRadiusTokens(),
    val elevation: ElevationTokens = ElevationTokens(),
    val iconSize: IconSizeTokens = IconSizeTokens(),
)

data class SpacingTokens(
    val xxxs: Dp = 2.dp,
    val xxs: Dp = 4.dp,
    val xs: Dp = 8.dp,
    val sm: Dp = 12.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 20.dp,
    val xl: Dp = 24.dp,
    val xxl: Dp = 32.dp,
    val xxxl: Dp = 40.dp,
    val huge: Dp = 48.dp,
)

data class CornerRadiusTokens(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 20.dp,
    val full: Dp = 30.dp,
)

data class ElevationTokens(
    val none: Dp = 0.dp,
    val xs: Dp = 2.dp,
    val sm: Dp = 4.dp,
    val md: Dp = 8.dp,
    val lg: Dp = 12.dp,
    val xl: Dp = 16.dp,
    val xxl: Dp = 24.dp,
)

data class IconSizeTokens(
    val small: Dp = 16.dp,
    val medium: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 40.dp,
)

val LocalDimensionTokens = staticCompositionLocalOf { DimensionTokens() }

object AnimationTokens {
    object Duration {
        const val Instant: Int = 150
        const val Short: Int = 250
        const val Medium: Int = 350
        const val Long: Int = 500
        const val ExtraLong: Int = 700
        const val Shimmer: Int = 1400
        const val CarouselDelay: Int = 4000
        const val SnackBar: Int = 320
    }

    object Spring {
        val Default = spring<Float>(
            dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
            stiffness = androidx.compose.animation.core.Spring.StiffnessMedium,
        )
        val Gentle = spring<Float>(
            dampingRatio = androidx.compose.animation.core.Spring.DampingRatioHighBouncy,
            stiffness = androidx.compose.animation.core.Spring.StiffnessLow,
        )
        val Stiff = spring<Float>(
            dampingRatio = androidx.compose.animation.core.Spring.DampingRatioNoBouncy,
            stiffness = androidx.compose.animation.core.Spring.StiffnessHigh,
        )
    }

    object Tween {
        val Standard = tween<Float>(Duration.Medium, easing = FastOutSlowInEasing)
        val Quick = tween<Float>(Duration.Short, easing = FastOutSlowInEasing)
        val Slower = tween<Float>(Duration.Long, easing = FastOutSlowInEasing)
    }

    const val StaggerDelay: Int = 100
}
