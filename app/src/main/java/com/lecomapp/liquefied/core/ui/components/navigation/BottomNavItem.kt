package com.lecomapp.liquefied.core.ui.components.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.config.navigation.Route

data class BottomNavItem(
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int,
    @DrawableRes val selectedIconRes: Int,
    val route: Route,
)

val capsuleNavItems = listOf(
    BottomNavItem(
        labelRes = R.string.bottom_nav_home,
        iconRes = R.drawable.home_unselected,
        selectedIconRes = R.drawable.home_selected,
        route = Route.Home,
    ),
    BottomNavItem(
        labelRes = R.string.bottom_nav_categories,
        iconRes = R.drawable.category_unselected,
        selectedIconRes = R.drawable.category_selected,
        route = Route.ProductSearch,
    ),
    BottomNavItem(
        labelRes = R.string.bottom_nav_orders,
        iconRes = R.drawable.orders_unselected,
        selectedIconRes = R.drawable.order_selected,
        route = Route.Orders,
    ),
    BottomNavItem(
        labelRes = R.string.bottom_nav_profile,
        iconRes = R.drawable.profile_unselected,
        selectedIconRes = R.drawable.profile_selected,
        route = Route.Profile,
    ),
)
