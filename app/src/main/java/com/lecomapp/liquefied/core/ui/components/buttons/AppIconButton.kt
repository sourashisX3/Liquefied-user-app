package com.lecomapp.liquefied.core.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class IconButtonVariant {
    FILLED, OUTLINED, TEXT
}

enum class IconButtonSize {
    SMALL, MEDIUM, LARGE
}

@Composable
fun AppIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    painter: Painter? = null,
    contentDescription: String? = null,
    variant: IconButtonVariant = IconButtonVariant.FILLED,
    size: IconButtonSize = IconButtonSize.MEDIUM,
    enabled: Boolean = true,
    tint: Color? = null,
) {
    val diameter: Dp = when (size) {
        IconButtonSize.SMALL -> 32.dp
        IconButtonSize.MEDIUM -> 40.dp
        IconButtonSize.LARGE -> 48.dp
    }
    val iconSize: Dp = when (size) {
        IconButtonSize.SMALL -> 16.dp
        IconButtonSize.MEDIUM -> 20.dp
        IconButtonSize.LARGE -> 24.dp
    }
    val iconPadding: Dp = when (size) {
        IconButtonSize.SMALL -> 3.dp
        IconButtonSize.MEDIUM -> 4.dp
        IconButtonSize.LARGE -> 5.dp
    }

    val containerColor = when (variant) {
        IconButtonVariant.FILLED -> MaterialTheme.colorScheme.primary
        IconButtonVariant.OUTLINED, IconButtonVariant.TEXT -> Color.Transparent
    }
    val contentColor = when (variant) {
        IconButtonVariant.FILLED -> MaterialTheme.colorScheme.onPrimary
        IconButtonVariant.OUTLINED, IconButtonVariant.TEXT -> MaterialTheme.colorScheme.secondary
    }
    val border = when (variant) {
        IconButtonVariant.OUTLINED -> BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary)
        else -> null
    }
    val disabledContainer = containerColor.copy(alpha = 0.38f)
    val disabledContent = contentColor.copy(alpha = 0.38f)

    val haptic = LocalHapticFeedback.current

    Surface(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = modifier.size(diameter),
        enabled = enabled,
        shape = CircleShape,
        color = containerColor,
        contentColor = contentColor,
        border = border,
        shadowElevation = if (variant == IconButtonVariant.FILLED) 2.dp else 0.dp,
    ) {
        val iconTint = when {
            tint != null -> tint
            painter != null -> Color.Unspecified
            !enabled -> disabledContent
            else -> contentColor
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(iconPadding),
            contentAlignment = Alignment.Center,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(iconSize),
                    tint = iconTint,
                )
            } else if (painter != null) {
                Icon(
                    painter = painter,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(iconSize),
                    tint = iconTint,
                )
            }
        }
    }
}
