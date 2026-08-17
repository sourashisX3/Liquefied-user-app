package com.lecomapp.liquefied.features.profile.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.buttons.AppButton
import com.lecomapp.liquefied.core.ui.components.buttons.ButtonSize
import com.lecomapp.liquefied.core.ui.components.buttons.ButtonVariant
import com.lecomapp.liquefied.core.ui.components.common.DestinationScreen
import com.lecomapp.liquefied.core.ui.components.feedback.ErrorView
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.ThemeMode
import com.lecomapp.liquefied.features.profile.presentation.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    val subtitle = state.user?.let { listOfNotNull(it.firstName, it.lastName).joinToString(" ").ifBlank { null } }

    DestinationScreen(
        title = stringResource(R.string.profile_title),
        subtitle = subtitle,
    ) {
        when {
            state.isLoading -> Unit
            state.user != null -> ProfileContent(
                state = state,
                themeMode = themeMode,
                onThemeModeChange = viewModel::setThemeMode,
                onLogout = viewModel::logout,
            )
            else -> ErrorView(
                title = stringResource(R.string.profile_error_title),
                subtitle = stringResource(R.string.profile_error_subtitle),
                onRetry = viewModel::loadProfile,
            )
        }
    }
}

@Composable
private fun ProfileContent(
    state: com.lecomapp.liquefied.features.profile.presentation.viewmodels.ProfileUiState,
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    onLogout: () -> Unit,
) {
    val user = state.user ?: return
    val fullName = listOfNotNull(user.firstName, user.lastName).joinToString(" ").ifBlank { "—" }
    val email = user.email.orEmpty().ifBlank { null }
    val phone = listOfNotNull(user.dialCode, user.phoneNumber).joinToString(" ").ifBlank { null }
    val memberSince = user.createdAt.take(10)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = AppSpacing.lg),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(AppCornerRadius.large),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        ) {
            Row(
                modifier = Modifier.padding(AppSpacing.lg),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center,
                ) {
                    if (!user.profilePictureUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = user.profilePictureUrl,
                            contentDescription = fullName,
                            modifier = Modifier.fillMaxSize(),
                        )
                    } else {
                        Text(
                            text = fullName.firstOrNull()?.toString()?.uppercase() ?: "U",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(AppSpacing.lg))
                Column {
                    Text(
                        text = fullName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    email?.let {
                        Spacer(modifier = Modifier.height(AppSpacing.xxs))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    phone?.let {
                        Spacer(modifier = Modifier.height(AppSpacing.xxs))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(AppSpacing.xl))

        Text(
            text = stringResource(R.string.profile_member_since),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(AppSpacing.xxs))
        Text(
            text = memberSince,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(AppSpacing.xl))

        Text(
            text = stringResource(R.string.profile_theme),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(AppSpacing.sm))
        Row(horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm)) {
            ThemeChip(
                label = stringResource(R.string.profile_theme_system),
                selected = themeMode == ThemeMode.SYSTEM,
                onClick = { onThemeModeChange(ThemeMode.SYSTEM) },
            )
            ThemeChip(
                label = stringResource(R.string.profile_theme_light),
                selected = themeMode == ThemeMode.LIGHT,
                onClick = { onThemeModeChange(ThemeMode.LIGHT) },
            )
            ThemeChip(
                label = stringResource(R.string.profile_theme_dark),
                selected = themeMode == ThemeMode.DARK,
                onClick = { onThemeModeChange(ThemeMode.DARK) },
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.xl))

        AppButton(
            onClick = onLogout,
            text = stringResource(R.string.profile_logout),
            variant = ButtonVariant.DANGER,
            size = ButtonSize.MEDIUM,
            isLoading = state.isLoggingOut,
            leadingIcon = Icons.AutoMirrored.Outlined.Logout,
        )
    }
}

@Composable
private fun ThemeChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label, style = MaterialTheme.typography.labelLarge) },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = MaterialTheme.colorScheme.outlineVariant,
            selectedBorderColor = Color.Transparent,
        ),
    )
}