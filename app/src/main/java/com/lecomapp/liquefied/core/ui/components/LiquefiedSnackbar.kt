package com.lecomapp.liquefied.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.theme.LocalSnackbarColors
import com.lecomapp.liquefied.core.utils.SnackbarType

class TypedSnackbarState {
    var currentType: SnackbarType by mutableStateOf(SnackbarType.INFO)
}

@Composable
fun rememberTypedSnackbarState(): TypedSnackbarState {
    return remember { TypedSnackbarState() }
}

@Composable
fun TypedSnackbar(
    data: SnackbarData,
    typeState: TypedSnackbarState,
    modifier: Modifier = Modifier,
) {
    val snackbarColors = LocalSnackbarColors.current

    val containerColor by animateColorAsState(
        targetValue = when (typeState.currentType) {
            SnackbarType.SUCCESS -> snackbarColors.successContainer
            SnackbarType.ERROR -> snackbarColors.errorContainer
            SnackbarType.WARNING -> snackbarColors.warningContainer
            SnackbarType.INFO -> snackbarColors.infoContainer
        },
        label = "snackbarContainerColor",
    )

    val contentColor by animateColorAsState(
        targetValue = when (typeState.currentType) {
            SnackbarType.SUCCESS -> snackbarColors.onSuccessContainer
            SnackbarType.ERROR -> snackbarColors.onErrorContainer
            SnackbarType.WARNING -> snackbarColors.onWarningContainer
            SnackbarType.INFO -> snackbarColors.onInfoContainer
        },
        label = "snackbarContentColor",
    )

    Snackbar(
        snackbarData = data,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        containerColor = containerColor,
        contentColor = contentColor,
        actionContentColor = contentColor.copy(alpha = 0.9f),
        dismissActionContentColor = contentColor.copy(alpha = 0.7f),
    )
}
