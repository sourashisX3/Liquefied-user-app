package com.lecomapp.liquefied.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = LiquefiedColors.Primary.primary,
    onPrimary = LiquefiedColors.Primary.onPrimary,
    primaryContainer = LiquefiedColors.Primary.primaryContainer,
    onPrimaryContainer = LiquefiedColors.Primary.onPrimaryContainer,
    secondary = LiquefiedColors.Secondary.secondary,
    onSecondary = LiquefiedColors.Secondary.onSecondary,
    secondaryContainer = LiquefiedColors.Secondary.secondaryContainer,
    onSecondaryContainer = LiquefiedColors.Secondary.onSecondaryContainer,
    tertiary = LiquefiedColors.Tertiary.tertiary,
    onTertiary = LiquefiedColors.Tertiary.onTertiary,
    tertiaryContainer = LiquefiedColors.Tertiary.tertiaryContainer,
    onTertiaryContainer = LiquefiedColors.Tertiary.onTertiaryContainer,
    error = LiquefiedColors.Error.error,
    onError = LiquefiedColors.Error.onError,
    errorContainer = LiquefiedColors.Error.errorContainer,
    onErrorContainer = LiquefiedColors.Error.onErrorContainer,
    background = LiquefiedColors.Neutral.background,
    onBackground = LiquefiedColors.Neutral.onBackground,
    surface = LiquefiedColors.Neutral.surface,
    onSurface = LiquefiedColors.Neutral.onSurface,
    surfaceVariant = LiquefiedColors.Neutral.surfaceVariant,
    onSurfaceVariant = LiquefiedColors.Neutral.onSurfaceVariant,
    outline = LiquefiedColors.Neutral.outline,
    outlineVariant = LiquefiedColors.Neutral.outlineVariant,
    inverseSurface = LiquefiedColors.Neutral.inverseSurface,
    inverseOnSurface = LiquefiedColors.Neutral.inverseOnSurface,
    inversePrimary = LiquefiedColors.Neutral.inversePrimary,
    surfaceTint = LiquefiedColors.Neutral.surfaceTint,
    scrim = LiquefiedColors.Neutral.scrim,
)

private val DarkColorScheme = darkColorScheme(
    primary = LiquefiedColors.PrimaryDark.primary,
    onPrimary = LiquefiedColors.PrimaryDark.onPrimary,
    primaryContainer = LiquefiedColors.PrimaryDark.primaryContainer,
    onPrimaryContainer = LiquefiedColors.PrimaryDark.onPrimaryContainer,
    secondary = LiquefiedColors.SecondaryDark.secondary,
    onSecondary = LiquefiedColors.SecondaryDark.onSecondary,
    secondaryContainer = LiquefiedColors.SecondaryDark.secondaryContainer,
    onSecondaryContainer = LiquefiedColors.SecondaryDark.onSecondaryContainer,
    tertiary = LiquefiedColors.TertiaryDark.tertiary,
    onTertiary = LiquefiedColors.TertiaryDark.onTertiary,
    tertiaryContainer = LiquefiedColors.TertiaryDark.tertiaryContainer,
    onTertiaryContainer = LiquefiedColors.TertiaryDark.onTertiaryContainer,
    error = LiquefiedColors.ErrorDark.error,
    onError = LiquefiedColors.ErrorDark.onError,
    errorContainer = LiquefiedColors.ErrorDark.errorContainer,
    onErrorContainer = LiquefiedColors.ErrorDark.onErrorContainer,
    background = LiquefiedColors.NeutralDark.background,
    onBackground = LiquefiedColors.NeutralDark.onBackground,
    surface = LiquefiedColors.NeutralDark.surface,
    onSurface = LiquefiedColors.NeutralDark.onSurface,
    surfaceVariant = LiquefiedColors.NeutralDark.surfaceVariant,
    onSurfaceVariant = LiquefiedColors.NeutralDark.onSurfaceVariant,
    outline = LiquefiedColors.NeutralDark.outline,
    outlineVariant = LiquefiedColors.NeutralDark.outlineVariant,
    inverseSurface = LiquefiedColors.NeutralDark.inverseSurface,
    inverseOnSurface = LiquefiedColors.NeutralDark.inverseOnSurface,
    inversePrimary = LiquefiedColors.NeutralDark.inversePrimary,
    surfaceTint = LiquefiedColors.NeutralDark.surfaceTint,
    scrim = LiquefiedColors.NeutralDark.scrim,
)

@Composable
fun LiquefiedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    val snackbarColors = if (darkTheme) DarkSnackbarColors else LightSnackbarColors

    CompositionLocalProvider(
        LocalDimensionTokens provides DimensionTokens(),
        LocalSnackbarColors provides snackbarColors,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = LiquefiedTypography,
            shapes = LiquefiedShapes,
            content = content
        )
    }
}
