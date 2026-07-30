package com.lecomapp.liquefied.core.config.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: Route,
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Default.Home, Route.Home),
    BottomNavItem("Categories", Icons.Default.Category, Route.ProductSearch),
    BottomNavItem("Cart", Icons.Default.ShoppingCart, Route.Cart),
    BottomNavItem("Orders", Icons.Default.ViewList, Route.Orders),
    BottomNavItem("Profile", Icons.Default.Person, Route.Profile),
)
