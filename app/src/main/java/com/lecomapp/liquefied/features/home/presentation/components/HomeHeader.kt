package com.lecomapp.liquefied.features.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.common.AppHeaderContainer
import com.lecomapp.liquefied.core.ui.components.common.AppLogoSection
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.utils.UiText

@Composable
fun HomeHeader(
    tagline: UiText,
    walletBalance: Double?,
    unreadNotificationCount: Long,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppHeaderContainer(modifier = modifier) {
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
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(R.string.home_search),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable(onClick = onSearchClick),
                )
                Spacer(modifier = Modifier.width(AppSpacing.sm))
                HomeNotificationIcon(
                    unreadCount = unreadNotificationCount,
                    onClick = onNotificationsClick,
                )
            }
        }
    }
}