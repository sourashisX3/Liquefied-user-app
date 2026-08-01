package com.lecomapp.liquefied.core.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class SnackBarColors(
    val successContainer: Color,
    val onSuccessContainer: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,
    val infoContainer: Color,
    val onInfoContainer: Color,
)

val LocalSnackBarColors = compositionLocalOf {
    SnackBarColors(
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

val LightSnackBarColors = SnackBarColors(
    successContainer = LiquefiedColors.Success.color,
    onSuccessContainer = LiquefiedColors.Success.onColor,
    errorContainer = LiquefiedColors.Error.error,
    onErrorContainer = LiquefiedColors.Error.onError,
    warningContainer = LiquefiedColors.Warning.color,
    onWarningContainer = LiquefiedColors.Warning.onColor,
    infoContainer = LiquefiedColors.Info.color,
    onInfoContainer = LiquefiedColors.Info.onColor,
)

val DarkSnackBarColors = SnackBarColors(
    successContainer = LiquefiedColors.SuccessDark.container,
    onSuccessContainer = LiquefiedColors.SuccessDark.onContainer,
    errorContainer = LiquefiedColors.ErrorDark.errorContainer,
    onErrorContainer = LiquefiedColors.ErrorDark.onErrorContainer,
    warningContainer = LiquefiedColors.WarningDark.container,
    onWarningContainer = LiquefiedColors.WarningDark.onContainer,
    infoContainer = LiquefiedColors.InfoDark.container,
    onInfoContainer = LiquefiedColors.InfoDark.onContainer,
)
