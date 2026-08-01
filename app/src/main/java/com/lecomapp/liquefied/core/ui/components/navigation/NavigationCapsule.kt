package com.lecomapp.liquefied.core.ui.components.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.config.navigation.Route
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
    val resolvedSelectedItem = selectedItem ?: items.firstOrNull()
    val selectedIndex = items.indexOfFirst { it.route == resolvedSelectedItem?.route }.coerceAtLeast(0)
    val interactionSource = remember { MutableInteractionSource() }
    val density = LocalDensity.current
    val contentWidths = remember { mutableStateMapOf<Route, Int>() }

    BoxWithConstraints(
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
        val slotWidth = (maxWidth - AppSpacing.xxs * 2) / items.size
        val selectedContentWidth = contentWidths[resolvedSelectedItem?.route]?.let { with(density) { it.toDp() } }
            ?: AppSpacing.xxhuge
        val pillWidthTarget = (selectedContentWidth + AppSpacing.xs * 2).coerceAtLeast(AppSpacing.xxhuge)
        val animatedWidth by animateDpAsState(
            targetValue = pillWidthTarget,
            animationSpec = spring(
                dampingRatio = 0.9f,
                stiffness = Spring.StiffnessMedium,
            ),
            label = "selectedPillWidth",
        )
        val animatedOffset by animateDpAsState(
            targetValue = (
                AppSpacing.xxs + slotWidth * selectedIndex + (slotWidth - animatedWidth) / 2
                ).coerceIn(
                AppSpacing.xxs + AppSpacing.xxxs,
                maxWidth - animatedWidth - AppSpacing.xxs - AppSpacing.xxxs,
            ),
            animationSpec = spring(
                dampingRatio = 0.9f,
                stiffness = Spring.StiffnessMedium,
            ),
            label = "selectedPillOffset",
        )

        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .fillMaxHeight()
                .padding(vertical = AppSpacing.xxs + AppSpacing.xxxs)
                .width(animatedWidth)
                .clip(RoundedCornerShape(AppCornerRadius.full))
                .background(
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(AppCornerRadius.full),
                )
                .indication(
                    interactionSource = interactionSource,
                    indication = ripple(
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.12f),
                    ),
                ),
        )

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.xxs, vertical = AppSpacing.xxs),
        ) {
            items.forEach { item ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { onItemClick(item) },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    NavigationNavItem(
                        item = item,
                        selected = item.route == resolvedSelectedItem?.route,
                        onContentWidthChanged = { contentWidths[item.route] = it },
                    )
                }
            }
        }
    }
}
