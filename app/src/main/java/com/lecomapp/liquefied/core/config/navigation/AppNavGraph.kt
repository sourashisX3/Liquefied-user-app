package com.lecomapp.liquefied.core.config.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.lecomapp.liquefied.core.ui.components.TypedSnackbar
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
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState) { data ->
                        TypedSnackbar(data = data, typeState = typedSnackbarState)
                    }
                },
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
                composable<Route.Splash> {
                    SplashScreen(navController = navController)
                }

                // Auth Graph
                composable<Route.Login> {
                    LoginScreen(
                        onNavigateToHome = { navController.navigate(Route.Home) },
                        onNavigateToRegister = { navController.navigate(Route.Register) },
                        onNavigateToForgotPassword = { navController.navigate(Route.ForgotPassword) },
                    )
                }
                composable<Route.Register> {
                    // TODO: RegisterScreen(navController)
                    Text("Register Screen")
                }
                composable<Route.OtpVerification> {
                    // TODO: OtpVerificationScreen(navController)
                    Text("OTP Screen")
                }
                composable<Route.ForgotPassword> {
                    // TODO: ForgotPasswordScreen(navController)
                    Text("Forgot Password Screen")
                }

                // Main Graph
                composable<Route.Home> {
                    // TODO: HomeScreen(navController)
                    Text("Home Screen")
                }
                composable<Route.ProductDetail> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.ProductDetail>()
                    // TODO: ProductDetailScreen(uuid = route.uuid)
                    Text("Product Detail: ${route.uuid}")
                }
                composable<Route.ProductSearch> {
                    // TODO: ProductSearchScreen(navController)
                    Text("Search Screen")
                }
                composable<Route.Cart> {
                    // TODO: CartScreen(navController)
                    Text("Cart Screen")
                }
                composable<Route.Checkout> {
                    // TODO: CheckoutScreen(navController)
                    Text("Checkout Screen")
                }
                composable<Route.Orders> {
                    // TODO: OrderListScreen(navController)
                    Text("Orders Screen")
                }
                composable<Route.OrderDetail> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.OrderDetail>()
                    // TODO: OrderDetailScreen(uuid = route.uuid)
                    Text("Order Detail: ${route.uuid}")
                }
                composable<Route.Wishlist> {
                    // TODO: WishlistScreen(navController)
                    Text("Wishlist Screen")
                }
                composable<Route.WriteReview> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.WriteReview>()
                    // TODO: WriteReviewScreen(productUuid = route.productUuid)
                    Text("Write Review: ${route.productUuid}")
                }
                composable<Route.AddressList> {
                    // TODO: AddressListScreen(navController)
                    Text("Address List")
                }
                composable<Route.AddressForm> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.AddressForm>()
                    // TODO: AddressFormScreen(uuid = route.uuid)
                    Text("Address Form: ${route.uuid}")
                }
                composable<Route.Wallet> {
                    // TODO: WalletScreen(navController)
                    Text("Wallet Screen")
                }
                composable<Route.Notifications> {
                    // TODO: NotificationScreen(navController)
                    Text("Notifications")
                }
                composable<Route.ChatList> {
                    // TODO: ChatListScreen(navController)
                    Text("Chat List")
                }
                composable<Route.ChatDetail> { backStackEntry ->
                    val route = backStackEntry.toRoute<Route.ChatDetail>()
                    // TODO: ChatDetailScreen(roomUuid = route.roomUuid)
                    Text("Chat Detail: ${route.roomUuid}")
                }
                composable<Route.Profile> {
                    // TODO: ProfileScreen(navController)
                    Text("Profile Screen")
                }
                composable<Route.EditProfile> {
                    // TODO: EditProfileScreen(navController)
                    Text("Edit Profile")
                }
            }
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
