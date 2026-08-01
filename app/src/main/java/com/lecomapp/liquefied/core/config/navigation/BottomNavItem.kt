package com.lecomapp.liquefied.core.config.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.ui.graphics.vector.ImageVector
import com.lecomapp.liquefied.R

data class BottomNavItem(
    @StringRes val labelRes: Int,
    val icon: ImageVector,
    val route: Route,
)

val bottomNavItems = listOf(
    BottomNavItem(R.string.bottom_nav_home, Icons.Default.Home, Route.Home),
    BottomNavItem(R.string.bottom_nav_categories, Icons.Default.Category, Route.ProductSearch),
    BottomNavItem(R.string.bottom_nav_cart, Icons.Default.ShoppingCart, Route.Cart),
    BottomNavItem(R.string.bottom_nav_orders, Icons.Default.ViewList, Route.Orders),
    BottomNavItem(R.string.bottom_nav_profile, Icons.Default.Person, Route.Profile),
)
