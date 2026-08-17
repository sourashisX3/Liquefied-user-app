package com.lecomapp.liquefied.features.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.common.AnimatedDiamonds
import com.lecomapp.liquefied.core.ui.components.common.AppHeaderContainer
import com.lecomapp.liquefied.core.ui.components.common.AppIcons
import com.lecomapp.liquefied.core.ui.components.common.AppLogoSection
import com.lecomapp.liquefied.core.ui.components.common.DiamondConfig
import com.lecomapp.liquefied.core.ui.theme.AppIconSize
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.utils.UiText

private val homeHeaderDiamonds = listOf(
    DiamondConfig(Offset(0.92f, 0.08f), 72f, 0.20f, 5500, 2600, 0),
    DiamondConfig(Offset(0.80f, 0.22f), 56f, 0.16f, 7200, 3200, 250),
    DiamondConfig(Offset(0.97f, 0.28f), 44f, 0.14f, 6400, 2800, 120),
    DiamondConfig(Offset(0.68f, 0.10f), 40f, 0.15f, 8000, 3600, 450),
    DiamondConfig(Offset(0.86f, 0.44f), 36f, 0.13f, 5800, 3000, 300),
    DiamondConfig(Offset(0.60f, 0.32f), 34f, 0.12f, 9000, 4000, 600),
)

@Composable
fun HomeHeader(
    tagline: UiText,
    walletBalance: Double?,
    unreadNotificationCount: Long,
    address: String?,
    isLocating: Boolean,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onAddressClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppHeaderContainer(
        modifier = modifier,
        backgroundContent = {
            AnimatedDiamonds(
                modifier = Modifier.matchParentSize(),
                baseColor = MaterialTheme.colorScheme.onPrimary,
                diamonds = homeHeaderDiamonds,
            )
        },
    ) {
        Column {
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    AppLogoSection(
                        horizontalAlignment = Alignment.Start,
                        tagline = tagline.asString(),
                        appNameStyle = MaterialTheme.typography.headlineLarge,
                        appNameMaxLines = 1,
                        appNameSoftWrap = false,
                    )
                }
                Spacer(modifier = Modifier.width(AppSpacing.sm))
                Row(verticalAlignment = Alignment.Top) {
                    HomeBalancePill(walletBalance = walletBalance)
                    Spacer(modifier = Modifier.width(AppSpacing.sm))
                    Icon(
                        imageVector = AppIcons.Action.Search,
                        contentDescription = stringResource(R.string.home_search),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(AppIconSize.headerAction)
                            .clickable(onClick = onSearchClick),
                    )
                    Spacer(modifier = Modifier.width(AppSpacing.sm))
                    HomeNotificationIcon(
                        unreadCount = unreadNotificationCount,
                        onClick = onNotificationsClick,
                    )
                }
}
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(enabled = !isLocating, onClick = onAddressClick)
                    .padding(top = AppSpacing.xxs, bottom = AppSpacing.xxs),
            ) {
                if (isLocating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(AppIconSize.small),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                } else {
                    Icon(
                        imageVector = AppIcons.Action.Location,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(AppIconSize.small),
                    )
                }
                Spacer(modifier = Modifier.width(AppSpacing.xxs))
                Text(
                    text = when {
                        isLocating -> stringResource(R.string.home_locating)
                        address != null -> address
                        else -> stringResource(R.string.home_detect_location)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = when {
                        isLocating -> FontWeight.Normal
                        address != null -> FontWeight.Medium
                        else -> FontWeight.SemiBold
                    },
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (!isLocating) {
                    Spacer(modifier = Modifier.width(AppSpacing.xxs))
                    Icon(
                        imageVector = AppIcons.Action.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                        modifier = Modifier.size(AppIconSize.small),
                    )
                }
            }
        }
    }
}