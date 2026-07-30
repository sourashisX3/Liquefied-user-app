package com.lecomapp.liquefied.core.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class SnackbarColors(
    val successContainer: Color,
    val onSuccessContainer: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,
    val infoContainer: Color,
    val onInfoContainer: Color,
)

val LocalSnackbarColors = compositionLocalOf {
    SnackbarColors(
        successContainer = Color.Unspecified,
        onSuccessContainer = Color.Unspecified,
        errorContainer = Color.Unspecified,
        onErrorContainer = Color.Unspecified,
        warningContainer = Color.Unspecified,
        onWarningContainer = Color.Unspecified,
        infoContainer = Color.Unspecified,
        onInfoContainer = Color.Unspecified,
    )
}

val LightSnackbarColors = SnackbarColors(
    successContainer = LiquefiedColors.Success.container,
    onSuccessContainer = LiquefiedColors.Success.onContainer,
    errorContainer = LiquefiedColors.Error.errorContainer,
    onErrorContainer = LiquefiedColors.Error.onErrorContainer,
    warningContainer = LiquefiedColors.Warning.container,
    onWarningContainer = LiquefiedColors.Warning.onContainer,
    infoContainer = LiquefiedColors.Info.container,
    onInfoContainer = LiquefiedColors.Info.onContainer,
)

val DarkSnackbarColors = SnackbarColors(
    successContainer = LiquefiedColors.SuccessDark.container,
    onSuccessContainer = LiquefiedColors.SuccessDark.onContainer,
    errorContainer = LiquefiedColors.ErrorDark.errorContainer,
    onErrorContainer = LiquefiedColors.ErrorDark.onErrorContainer,
    warningContainer = LiquefiedColors.WarningDark.container,
    onWarningContainer = LiquefiedColors.WarningDark.onContainer,
    infoContainer = LiquefiedColors.InfoDark.container,
    onInfoContainer = LiquefiedColors.InfoDark.onContainer,
)
