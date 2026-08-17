package com.lecomapp.liquefied.core.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.theme.AppButton
import com.lecomapp.liquefied.core.ui.theme.AppElevation
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.ComponentSize
import com.lecomapp.liquefied.core.ui.theme.ShapeTokens

enum class ButtonVariant {
    PRIMARY, SECONDARY, TERTIARY, DANGER, OUTLINE, SECONDARY_OUTLINE
}

enum class ButtonSize {
    SMALL, MEDIUM, LARGE
}

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    variant: ButtonVariant = ButtonVariant.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
) {
    val (backgroundColor, contentColor, borderColor) = when (variant) {
        ButtonVariant.PRIMARY -> Triple(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            Color.Transparent
        )
        ButtonVariant.SECONDARY -> Triple(
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
            Color.Transparent
        )
        ButtonVariant.TERTIARY -> Triple(
            Color.Transparent,
            MaterialTheme.colorScheme.primary,
            Color.Transparent
        )
        ButtonVariant.DANGER -> Triple(
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.onError,
            Color.Transparent
        )
        ButtonVariant.SECONDARY_OUTLINE -> Triple(
            Color.Transparent,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.secondary
        )
        ButtonVariant.OUTLINE -> Triple(
            Color.Transparent,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary
        )
    }

    val buttonHeight = when (size) {
        ButtonSize.SMALL -> ComponentSize.buttonHeightSmall
        ButtonSize.MEDIUM -> ComponentSize.buttonHeightMedium
        ButtonSize.LARGE -> AppButton.height
    }

    val iconSize = when (size) {
        ButtonSize.SMALL -> 16.dp
        ButtonSize.MEDIUM -> 20.dp
        ButtonSize.LARGE -> 22.dp
    }

    val disabledBg = if (variant == ButtonVariant.PRIMARY) backgroundColor.copy(alpha = 0.38f) else backgroundColor
    val disabledContent = contentColor.copy(alpha = 0.38f)

    val haptic = LocalHapticFeedback.current

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        enabled = enabled && !isLoading,
        modifier = modifier.height(buttonHeight),
        shape = ShapeTokens.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = disabledBg,
            disabledContentColor = disabledContent,
        ),
        border = when (variant) {
            ButtonVariant.OUTLINE, ButtonVariant.SECONDARY_OUTLINE -> BorderStroke(1.5.dp, borderColor)
            else -> null
        },
        contentPadding = PaddingValues(horizontal = AppSpacing.xl),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (variant == ButtonVariant.PRIMARY) AppElevation.xs else AppElevation.none,
            pressedElevation = AppElevation.none,
        ),
        interactionSource = remember { MutableInteractionSource() },
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(iconSize),
                color = contentColor,
                strokeWidth = 2.dp,
            )
        } else {
            val density = LocalDensity.current
            var textWidth by remember { mutableStateOf(0) }
            val hasText = !text.isNullOrEmpty()
            val iconSideOffset = if (hasText && textWidth > 0) {
                with(density) {
                    (textWidth / 2f + iconSize.toPx() / 2f + AppSpacing.xs.toPx()).toDp()
                }
            } else {
                0.dp
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                if (hasText) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelLarge,
                        color = contentColor,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .onSizeChanged { textWidth = it.width },
                    )
                }
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = -iconSideOffset)
                            .size(iconSize),
                        tint = contentColor,
                    )
                }
                if (trailingIcon != null) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = iconSideOffset)
                            .size(iconSize),
                        tint = contentColor,
                    )
                }
            }
        }
    }
}
