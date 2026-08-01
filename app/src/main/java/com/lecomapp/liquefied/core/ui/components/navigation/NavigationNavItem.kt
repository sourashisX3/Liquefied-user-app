package com.lecomapp.liquefied.core.ui.components.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun NavigationNavItem(
    item: BottomNavItem,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onContentWidthChanged: (Int) -> Unit = {},
) {
    val tint by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.onSecondary.copy(alpha = if (selected) 1f else 0.6f),
        label = "navItemTint",
    )

    Row(
        modifier = modifier
            .wrapContentSize(unbounded = true)
            .onSizeChanged { onContentWidthChanged(it.width) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.xxs),
    ) {
        Icon(
            painter = painterResource(if (selected) item.selectedIconRes else item.iconRes),
            contentDescription = stringResource(item.labelRes),
            tint = tint,
            modifier = Modifier.size(18.dp),
        )
        AnimatedVisibility(
            visible = selected,
            enter = fadeIn() + expandHorizontally(expandFrom = Alignment.Start),
            exit = fadeOut() + shrinkHorizontally(shrinkTowards = Alignment.Start),
        ) {
            Text(
                text = stringResource(item.labelRes),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    letterSpacing = 0.sp,
                ),
                fontWeight = FontWeight.Bold,
                color = tint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
