package com.lecomapp.liquefied.features.profile.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.common.AppIcons
import com.lecomapp.liquefied.core.ui.components.common.AppOverflowMenu
import com.lecomapp.liquefied.core.ui.components.common.ShimmerSkeleton
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.utils.toDisplayDate
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser

@Composable
fun ProfileIdentityHeader(
    user: ProfileUser?,
    isUploading: Boolean,
    onPictureClick: () -> Unit,
    onEditProfileClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.Top) {
        ProfileAvatar(
            user = user,
            isUploading = isUploading,
            onClick = onPictureClick,
        )
        Spacer(modifier = Modifier.width(AppSpacing.lg))
        Column(modifier = Modifier.weight(1f)) {
            if (user == null) {
                ShimmerSkeleton(
                    modifier = Modifier.fillMaxWidth(0.55f).height(24.dp),
                    shape = RoundedCornerShape(AppCornerRadius.small),
                )
                Spacer(modifier = Modifier.height(AppSpacing.sm))
                ShimmerSkeleton(
                    modifier = Modifier.fillMaxWidth(0.75f).height(14.dp),
                    shape = RoundedCornerShape(AppCornerRadius.small),
                )
            } else {
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                user.email?.takeIf { it.isNotBlank() }?.let {
                    Spacer(modifier = Modifier.height(AppSpacing.xxs))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                    )
                }
                user.phone?.takeIf { it.isNotBlank() }?.let {
                    Spacer(modifier = Modifier.height(AppSpacing.xxs))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                    )
                }
                Spacer(modifier = Modifier.height(AppSpacing.xxs))
                Text(
                    text = stringResource(
                        R.string.profile_member_since_format,
                        user.createdAt.toDisplayDate(),
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                )
            }
        }
        Spacer(modifier = Modifier.width(AppSpacing.sm))
        AppOverflowMenu(
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.profile_more_options),
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.profile_edit_profile),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.Action.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                },
                onClick = onEditProfileClick,
            )
        }
    }
}