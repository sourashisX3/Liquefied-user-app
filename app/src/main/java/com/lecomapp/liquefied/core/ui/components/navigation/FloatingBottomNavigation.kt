package com.lecomapp.liquefied.core.ui.components.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lecomapp.liquefied.core.config.navigation.Route
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LiquefiedTheme

object FloatingNavigationDefaults {
    val bottomPadding: Dp = AppSpacing.md
}

@Composable
fun FloatingBottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val selectedItem = capsuleNavItems.firstOrNull { item ->
        currentDestination?.hasRoute(item.route::class) == true
    }

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = slideInVertically(animationSpec = tween(320)) { it } + fadeIn(animationSpec = tween(320)),
        exit = slideOutVertically(animationSpec = tween(240)) { it } + fadeOut(animationSpec = tween(240)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = AppSpacing.lg)
                .padding(bottom = FloatingNavigationDefaults.bottomPadding),
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavigationCapsule(
                items = capsuleNavItems,
                selectedItem = selectedItem,
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.weight(1f),
            )

            CartFloatingButton(
                onClick = {
                    navController.navigate(Route.Cart) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Preview(name = "Floating Navigation - Light", showBackground = true)
@Composable
private fun FloatingBottomNavigationLightPreview() {
    LiquefiedTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavigationCapsule(
                items = capsuleNavItems,
                selectedItem = capsuleNavItems.first(),
                onItemClick = {},
                modifier = Modifier.weight(1f),
            )
            CartFloatingButton(onClick = {})
        }
    }
}

@Preview(name = "Floating Navigation - Dark", showBackground = true)
@Composable
private fun FloatingBottomNavigationDarkPreview() {
    LiquefiedTheme(darkTheme = true) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavigationCapsule(
                items = capsuleNavItems,
                selectedItem = capsuleNavItems.first(),
                onItemClick = {},
                modifier = Modifier.weight(1f),
            )
            CartFloatingButton(onClick = {})
        }
    }
}
