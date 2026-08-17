package com.lecomapp.liquefied.core.ui.components.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.config.navigation.Route
import com.lecomapp.liquefied.core.ui.components.common.AppIcons

data class BottomNavItem(
    @param:StringRes val labelRes: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val route: Route,
)

val capsuleNavItems = listOf(
    BottomNavItem(
        labelRes = R.string.bottom_nav_home,
        icon = AppIcons.Nav.Home.first,
        selectedIcon = AppIcons.Nav.Home.second,
        route = Route.Home,
    ),
    BottomNavItem(
        labelRes = R.string.bottom_nav_categories,
        icon = AppIcons.Nav.Categories.first,
        selectedIcon = AppIcons.Nav.Categories.second,
        route = Route.ProductSearch,
    ),
    BottomNavItem(
        labelRes = R.string.bottom_nav_orders,
        icon = AppIcons.Nav.Orders.first,
        selectedIcon = AppIcons.Nav.Orders.second,
        route = Route.Orders,
    ),
    BottomNavItem(
        labelRes = R.string.bottom_nav_profile,
        icon = AppIcons.Nav.Profile.first,
        selectedIcon = AppIcons.Nav.Profile.second,
        route = Route.Profile,
    ),
)