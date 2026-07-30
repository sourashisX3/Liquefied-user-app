package com.lecomapp.liquefied.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.lecomapp.liquefied.R

@Composable
fun LiquefiedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = colorResource(R.color.primary_dark),
            onPrimary = colorResource(R.color.on_primary_dark),
            primaryContainer = colorResource(R.color.primary_container_dark),
            onPrimaryContainer = colorResource(R.color.on_primary_container_dark),
            secondary = colorResource(R.color.secondary_dark),
            onSecondary = colorResource(R.color.on_secondary_dark),
            secondaryContainer = colorResource(R.color.secondary_container_dark),
            onSecondaryContainer = colorResource(R.color.on_secondary_container_dark),
            tertiary = colorResource(R.color.tertiary_dark),
            onTertiary = colorResource(R.color.on_tertiary_dark),
            tertiaryContainer = colorResource(R.color.tertiary_container_dark),
            onTertiaryContainer = colorResource(R.color.on_tertiary_container_dark),
            error = colorResource(R.color.error_dark),
            onError = colorResource(R.color.on_error_dark),
            errorContainer = colorResource(R.color.error_container_dark),
            onErrorContainer = colorResource(R.color.on_error_container_dark),
            background = colorResource(R.color.background_dark),
            onBackground = colorResource(R.color.on_background_dark),
            surface = colorResource(R.color.surface_dark),
            onSurface = colorResource(R.color.on_surface_dark),
            surfaceVariant = colorResource(R.color.surface_variant_dark),
            onSurfaceVariant = colorResource(R.color.on_surface_variant_dark),
            outline = colorResource(R.color.outline_dark),
            outlineVariant = colorResource(R.color.outline_variant_dark),
            inverseSurface = colorResource(R.color.inverse_surface_dark),
            inverseOnSurface = colorResource(R.color.inverse_on_surface_dark),
            inversePrimary = colorResource(R.color.inverse_primary_dark),
            surfaceTint = colorResource(R.color.surface_tint_dark),
            scrim = colorResource(R.color.scrim_dark),
        )
    } else {
        lightColorScheme(
            primary = colorResource(R.color.primary),
            onPrimary = colorResource(R.color.on_primary),
            primaryContainer = colorResource(R.color.primary_container),
            onPrimaryContainer = colorResource(R.color.on_primary_container),
            secondary = colorResource(R.color.secondary),
            onSecondary = colorResource(R.color.on_secondary),
            secondaryContainer = colorResource(R.color.secondary_container),
            onSecondaryContainer = colorResource(R.color.on_secondary_container),
            tertiary = colorResource(R.color.tertiary),
            onTertiary = colorResource(R.color.on_tertiary),
            tertiaryContainer = colorResource(R.color.tertiary_container),
            onTertiaryContainer = colorResource(R.color.on_tertiary_container),
            error = colorResource(R.color.error),
            onError = colorResource(R.color.on_error),
            errorContainer = colorResource(R.color.error_container),
            onErrorContainer = colorResource(R.color.on_error_container),
            background = colorResource(R.color.background),
            onBackground = colorResource(R.color.on_background),
            surface = colorResource(R.color.surface),
            onSurface = colorResource(R.color.on_surface),
            surfaceVariant = colorResource(R.color.surface_variant),
            onSurfaceVariant = colorResource(R.color.on_surface_variant),
            outline = colorResource(R.color.outline),
            outlineVariant = colorResource(R.color.outline_variant),
            inverseSurface = colorResource(R.color.inverse_surface),
            inverseOnSurface = colorResource(R.color.inverse_on_surface),
            inversePrimary = colorResource(R.color.inverse_primary),
            surfaceTint = colorResource(R.color.surface_tint),
            scrim = colorResource(R.color.scrim),
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LiquefiedTypography,
        shapes = LiquefiedShapes,
        content = content
    )
}
