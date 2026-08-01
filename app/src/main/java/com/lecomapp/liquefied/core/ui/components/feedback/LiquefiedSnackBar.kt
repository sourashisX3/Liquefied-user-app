package com.lecomapp.liquefied.core.ui.components.feedback

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarColors
import com.lecomapp.liquefied.core.utils.SnackBarEvent
import com.lecomapp.liquefied.core.utils.SnackBarType

class TypedSnackBarState {
    var currentType: SnackBarType by mutableStateOf(SnackBarType.INFO)
}

@Composable
fun rememberTypedSnackBarState(): TypedSnackBarState {
    return remember { TypedSnackBarState() }
}

internal fun SnackBarType.displayDurationMillis(): Long = when (this) {
    SnackBarType.ERROR, SnackBarType.SUCCESS -> 10_000L
    else -> 4_000L
}

suspend fun SnackbarHostState.showTypedSnackBar(
    event: SnackBarEvent,
    context: Context,
    typeState: TypedSnackBarState,
) {
    currentSnackbarData?.dismiss()
    typeState.currentType = event.type
    showSnackbar(message = event.message.asString(context))
}

@Composable
fun TypedSnackBar(
    data: SnackbarData,
    typeState: TypedSnackBarState,
    modifier: Modifier = Modifier,
) {
    val snackBarColors = LocalSnackBarColors.current

    val containerColor = when (typeState.currentType) {
        SnackBarType.SUCCESS -> snackBarColors.successContainer
        SnackBarType.ERROR -> snackBarColors.errorContainer
        SnackBarType.WARNING -> snackBarColors.warningContainer
        SnackBarType.INFO -> snackBarColors.infoContainer
    }

    val contentColor = when (typeState.currentType) {
        SnackBarType.SUCCESS -> snackBarColors.onSuccessContainer
        SnackBarType.ERROR -> snackBarColors.onErrorContainer
        SnackBarType.WARNING -> snackBarColors.onWarningContainer
        SnackBarType.INFO -> snackBarColors.onInfoContainer
    }

    val actionLabel = data.visuals.actionLabel

    Snackbar(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        containerColor = containerColor,
        contentColor = contentColor,
        actionContentColor = contentColor.copy(alpha = 0.9f),
        action = {
            if (actionLabel != null) {
                TextButton(onClick = { data.dismiss() }) {
                    Text(text = actionLabel, color = contentColor)
                }
            }
        },
        dismissAction = {
            IconButton(onClick = { data.dismiss() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss",
                    tint = contentColor.copy(alpha = 0.8f),
                )
            }
        },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SnackBarIcon(
                type = typeState.currentType,
                tint = contentColor,
            )
            Spacer(modifier = Modifier.width(AppSpacing.sm))
            Text(
                text = data.visuals.message,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
