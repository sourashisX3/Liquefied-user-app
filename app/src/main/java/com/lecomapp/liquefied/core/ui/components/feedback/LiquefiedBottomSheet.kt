package com.lecomapp.liquefied.core.ui.components.feedback

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.buttons.AppButton
import com.lecomapp.liquefied.core.ui.components.buttons.ButtonSize
import com.lecomapp.liquefied.core.ui.components.buttons.ButtonVariant
import com.lecomapp.liquefied.core.ui.components.common.AppIcons
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppIconSize
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.ShapeTokens

data class SheetOption(
    val label: String,
    val selected: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiquefiedBottomSheet(
    title: String,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = ShapeTokens.bottomSheet,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.lg)
                .padding(bottom = AppSpacing.lg),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(AppSpacing.md))
            content()
        }
    }
}

@Composable
fun SheetOptionRow(
    icon: ImageVector? = null,
    flagRes: Int? = null,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppCornerRadius.large))
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                },
            )
            .clickable(onClick = onClick)
            .padding(horizontal = AppSpacing.md, vertical = AppSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (flagRes != null) {
            Image(
                painter = painterResource(flagRes),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 28.dp, height = 18.dp)
                    .clip(RoundedCornerShape(AppCornerRadius.extraSmall)),
            )
            Spacer(modifier = Modifier.width(AppSpacing.md))
        }
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.width(AppIconSize.small),
            )
            Spacer(modifier = Modifier.width(AppSpacing.md))
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier.weight(1f),
        )
        if (selected) {
            Spacer(modifier = Modifier.width(AppSpacing.sm))
            Icon(
                imageVector = AppIcons.Action.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.width(AppIconSize.medium),
            )
        }
    }
}

@Composable
fun LiquefiedOptionSheet(
    title: String,
    options: List<SheetOption>,
    onSelect: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    LiquefiedBottomSheet(
        title = title,
        onDismissRequest = onDismissRequest,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.xs)) {
            options.forEachIndexed { index, option ->
                SheetOptionRow(
                    label = option.label,
                    selected = option.selected,
                    onClick = { onSelect(index) },
                )
            }
        }
    }
}

@Composable
fun ConfirmSheet(
    title: String,
    message: String,
    confirmLabel: String,
    isLoading: Boolean = false,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    LiquefiedBottomSheet(
        title = title,
        onDismissRequest = onDismissRequest,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(AppSpacing.lg))
        Row(horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm)) {
            AppButton(
                onClick = onDismissRequest,
                text = stringResource(R.string.common_cancel),
                variant = ButtonVariant.TERTIARY,
                size = ButtonSize.MEDIUM,
                modifier = Modifier.weight(1f),
            )
            AppButton(
                onClick = onConfirm,
                text = confirmLabel,
                variant = ButtonVariant.DANGER,
                size = ButtonSize.MEDIUM,
                isLoading = isLoading,
                leadingIcon = AppIcons.Action.Logout,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun PictureSourceSheet(
    onCamera: () -> Unit,
    onGallery: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    LiquefiedBottomSheet(
        title = stringResource(R.string.profile_picture_source_title),
        onDismissRequest = onDismissRequest,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.xs)) {
            SheetOptionRow(
                icon = AppIcons.Action.PhotoCamera,
                label = stringResource(R.string.profile_picture_camera),
                selected = false,
                onClick = onCamera,
            )
            SheetOptionRow(
                icon = AppIcons.Action.PhotoLibrary,
                label = stringResource(R.string.profile_picture_gallery),
                selected = false,
                onClick = onGallery,
            )
        }
    }
}
