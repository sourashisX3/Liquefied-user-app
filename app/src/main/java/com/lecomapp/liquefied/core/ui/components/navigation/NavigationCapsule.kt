package com.lecomapp.liquefied.core.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppElevation
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

object NavigationCapsuleDefaults {
    val height: Dp = 56.dp
}

@Composable
fun NavigationCapsule(
    items: List<BottomNavItem>,
    selectedItem: BottomNavItem?,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(NavigationCapsuleDefaults.height)
            .shadow(
                elevation = AppElevation.sm,
                shape = RoundedCornerShape(AppCornerRadius.full),
                ambientColor = MaterialTheme.colorScheme.scrim,
                spotColor = MaterialTheme.colorScheme.scrim,
            )
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(AppCornerRadius.full),
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.xxs, vertical = AppSpacing.xxs),
        ) {
            items.forEach { item ->
                NavigationNavItem(
                    item = item,
                    selected = item.route == selectedItem?.route,
                    onClick = { onItemClick(item) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}
