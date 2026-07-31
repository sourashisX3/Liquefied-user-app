package com.lecomapp.liquefied.core.config.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.lecomapp.liquefied.core.ui.components.LiquefiedSnackbarHost
import com.lecomapp.liquefied.core.ui.components.rememberTypedSnackbarState
import com.lecomapp.liquefied.core.ui.theme.LiquefiedTheme
import com.lecomapp.liquefied.core.ui.theme.LocalSnackbarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackbarState
import com.lecomapp.liquefied.features.auth.presentation.screens.LoginScreen
import com.lecomapp.liquefied.features.splash.presentation.SplashScreen

@Composable
fun AppNavGraph() {
    LiquefiedTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val showBottomBar = bottomNavItems.any { item ->
            currentDestination?.hasRoute(item.route::class) == true
        }

        val snackbarHostState = remember { SnackbarHostState() }
        val typedSnackbarState = rememberTypedSnackbarState()

        CompositionLocalProvider(
            LocalSnackbarHostState provides snackbarHostState,
            LocalTypedSnackbarState provides typedSnackbarState,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            AppBottomBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.Splash,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        // Splash
                        composable<Route.Splash>(
                            exitTransition = { fadeOut(animationSpec = tween(500)) },
                        ) {
                            SplashScreen(navController = navController)
                        }

                        // Auth Graph
                        composable<Route.Login>(
                            enterTransition = {
                                slideInVertically(animationSpec = tween(600)) { it / 4 } + fadeIn(animationSpec = tween(600))
                            },
                        ) {
                            LoginScreen(
                                onNavigateToHome = { navController.navigate(Route.Home) },
                                onNavigateToRegister = { navController.navigate(Route.Register) },
                                onNavigateToForgotPassword = { navController.navigate(Route.ForgotPassword) },
                            )
                        }
                        composable<Route.Register> {
                            Text("Register Screen")
                        }
                        composable<Route.OtpVerification> {
                            Text("OTP Screen")
                        }
                        composable<Route.ForgotPassword> {
                            Text("Forgot Password Screen")
                        }

                        // Main Graph
                        composable<Route.Home> {
                            Text("Home Screen")
                        }
                        composable<Route.ProductDetail> { backStackEntry ->
                            val route = backStackEntry.toRoute<Route.ProductDetail>()
                            Text("Product Detail: ${route.uuid}")
                        }
                        composable<Route.ProductSearch> {
                            Text("Search Screen")
                        }
                        composable<Route.Cart> {
                            Text("Cart Screen")
                        }
                        composable<Route.Checkout> {
                            Text("Checkout Screen")
                        }
                        composable<Route.Orders> {
                            Text("Orders Screen")
                        }
                        composable<Route.OrderDetail> { backStackEntry ->
                            val route = backStackEntry.toRoute<Route.OrderDetail>()
                            Text("Order Detail: ${route.uuid}")
                        }
                        composable<Route.Wishlist> {
                            Text("Wishlist Screen")
                        }
                        composable<Route.WriteReview> { backStackEntry ->
                            val route = backStackEntry.toRoute<Route.WriteReview>()
                            Text("Write Review: ${route.productUuid}")
                        }
                        composable<Route.AddressList> {
                            Text("Address List")
                        }
                        composable<Route.AddressForm> { backStackEntry ->
                            val route = backStackEntry.toRoute<Route.AddressForm>()
                            Text("Address Form: ${route.uuid}")
                        }
                        composable<Route.Wallet> {
                            Text("Wallet Screen")
                        }
                        composable<Route.Notifications> {
                            Text("Notifications")
                        }
                        composable<Route.ChatList> {
                            Text("Chat List")
                        }
                        composable<Route.ChatDetail> { backStackEntry ->
                            val route = backStackEntry.toRoute<Route.ChatDetail>()
                            Text("Chat Detail: ${route.roomUuid}")
                        }
                        composable<Route.Profile> {
                            Text("Profile Screen")
                        }
                        composable<Route.EditProfile> {
                            Text("Edit Profile")
                        }
                    }
                }

                LiquefiedSnackbarHost(
                    hostState = snackbarHostState,
                    typeState = typedSnackbarState,
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }
        }
    }
}

@Composable
private fun AppBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentDestination?.hasRoute(item.route::class) == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}
