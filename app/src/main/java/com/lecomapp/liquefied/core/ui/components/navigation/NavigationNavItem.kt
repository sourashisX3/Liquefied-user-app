package com.lecomapp.liquefied.core.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun NavigationNavItem(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tint = MaterialTheme.colorScheme.onSecondary.copy(alpha = if (selected) 1f else 0.6f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AppSpacing.xxxs, vertical = AppSpacing.xxxs)
            .clip(RoundedCornerShape(AppCornerRadius.full))
            .background(
                color = if (selected) {
                    MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.15f)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(AppCornerRadius.full),
            )
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(if (selected) item.selectedIconRes else item.iconRes),
            contentDescription = stringResource(item.labelRes),
            tint = tint,
            modifier = Modifier.size(18.dp),
        )
        Text(
            text = stringResource(item.labelRes),
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                letterSpacing = 0.sp,
            ),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = AppSpacing.xxs),
        )
    }
}
