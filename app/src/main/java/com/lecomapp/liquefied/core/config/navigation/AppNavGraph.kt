package com.lecomapp.liquefied.core.config.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.EditOff
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.LocationOff
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.network.AuthEvents
import com.lecomapp.liquefied.core.ui.components.common.DestinationScreenEmpty
import com.lecomapp.liquefied.core.ui.components.feedback.EmptyState
import com.lecomapp.liquefied.core.ui.components.feedback.ErrorView
import com.lecomapp.liquefied.core.ui.components.feedback.LiquefiedSnackBarHost
import com.lecomapp.liquefied.core.ui.components.feedback.rememberTypedSnackBarState
import com.lecomapp.liquefied.core.ui.components.navigation.FloatingBottomNavigation
import com.lecomapp.liquefied.core.ui.components.navigation.capsuleNavItems
import com.lecomapp.liquefied.core.ui.theme.AppTheme
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackBarState
import com.lecomapp.liquefied.core.ui.theme.ThemeManager
import com.lecomapp.liquefied.core.ui.theme.ThemeMode
import com.lecomapp.liquefied.features.auth.presentation.screens.ForgotPasswordScreen
import com.lecomapp.liquefied.features.auth.presentation.screens.LoginScreen
import com.lecomapp.liquefied.features.auth.presentation.screens.OtpVerificationScreen
import com.lecomapp.liquefied.features.auth.presentation.screens.RegisterScreen
import com.lecomapp.liquefied.features.auth.presentation.screens.ResetPasswordScreen
import com.lecomapp.liquefied.features.onboarding.presentation.screens.OnboardingScreen
import com.lecomapp.liquefied.features.home.presentation.screens.HomeScreen
import com.lecomapp.liquefied.features.profile.presentation.screens.ProfileScreen
import com.lecomapp.liquefied.features.splash.presentation.SplashScreen
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AuthEventsEntryPoint {
    fun authEvents(): AuthEvents
}

@Composable
fun AppNavGraph() {
    val context = LocalContext.current
    val themeManager = remember { ThemeManager(context.applicationContext) }
    val themeMode by themeManager.themeMode.collectAsStateWithLifecycle(initialValue = ThemeMode.SYSTEM)

    AppTheme(themeMode = themeMode) {
        val navController = rememberNavController()
        val context = LocalContext.current
        val authEvents = remember {
            EntryPointAccessors.fromApplication(
                context.applicationContext,
                AuthEventsEntryPoint::class.java,
            ).authEvents()
        }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        LaunchedEffect(Unit) {
            authEvents.events.collect {
                navController.navigate(Route.Login) {
                    popUpTo(navController.graph.id) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }

        val showBottomBar = capsuleNavItems.any { item ->
            currentDestination?.hasRoute(item.route::class) == true
        } || currentDestination?.hasRoute(Route.Cart::class) == true

        val snackbarHostState = remember { SnackbarHostState() }
        val typedSnackBarState = rememberTypedSnackBarState()

        val navBarVisible = remember { mutableStateOf(true) }
        val navBarScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    if (source == NestedScrollSource.Drag && available.y != 0f) {
                        navBarVisible.value = available.y > 0f
                    }
                    return Offset.Zero
                }
            }
        }

        LaunchedEffect(currentDestination) {
            navBarVisible.value = true
        }

        CompositionLocalProvider(
            LocalSnackBarHostState provides snackbarHostState,
            LocalTypedSnackBarState provides typedSnackBarState,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(navBarScrollConnection),
            ) {
                Scaffold(
                    contentWindowInsets = WindowInsets(0),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.Splash,
                        modifier = Modifier
                            .padding(innerPadding),
                    ) {
                        // Splash
                        composable<Route.Splash>(
                            exitTransition = { fadeOut(animationSpec = tween(500)) },
                        ) {
                            SplashScreen(
                                onFinished = { isLoggedIn ->
                                    navController.navigate(if (isLoggedIn) Route.Home else Route.Onboarding) {
                                        popUpTo(Route.Splash) { inclusive = true }
                                    }
                                },
                            )
                        }

                        // Onboarding
                        composable<Route.Onboarding>(
                            enterTransition = {
                                fadeIn(animationSpec = tween(500)) + slideInVertically(animationSpec = tween(500)) { it / 8 }
                            },
                            exitTransition = { fadeOut(animationSpec = tween(300)) },
                        ) {
                            OnboardingScreen(
                                onFinish = {
                                    navController.navigate(Route.Login) {
                                        popUpTo(Route.Onboarding) { inclusive = true }
                                    }
                                },
                            )
                        }

                        // Auth Graph
                        composable<Route.Login>(
                            enterTransition = {
                                slideInVertically(animationSpec = tween(600)) { it / 4 } + fadeIn(animationSpec = tween(600))
                            },
                        ) {
                            LoginScreen(
                                onNavigateToHome = {
                                    navController.navigate(Route.Home) {
                                        popUpTo(Route.Login) { inclusive = true }
                                    }
                                },
                                onNavigateToRegister = { navController.navigate(Route.Register) },
                                onNavigateToForgotPassword = { navController.navigate(Route.ForgotPassword) },
                            )
                        }
                        composable<Route.Register> {
                            RegisterScreen(
                                onNavigateToHome = {
                                    navController.navigate(Route.Home) {
                                        popUpTo(Route.Register) { inclusive = true }
                                    }
                                },
                                onNavigateToLogin = { navController.popBackStack() },
                            )
                        }
                        composable<Route.ForgotPassword> {
                            ForgotPasswordScreen(
                                onNavigateToOtp = { identifier ->
                                    navController.navigate(Route.OtpVerification(identifier))
                                },
                                onNavigateToLogin = { navController.popBackStack() },
                            )
                        }
                        composable<Route.OtpVerification> { backStackEntry ->
                            OtpVerificationScreen(
                                identifier = backStackEntry.arguments?.getString("identifier").orEmpty(),
                                onNavigateToResetPassword = { identifier, otp ->
                                    navController.navigate(Route.ResetPassword(identifier, otp))
                                },
                            )
                        }
                        composable<Route.ResetPassword> {
                            ResetPasswordScreen(
                                onNavigateToLogin = {
                                    navController.navigate(Route.Login) {
                                        popUpTo(Route.ForgotPassword) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }

                        // Main Graph
                        composable<Route.Home> {
                            HomeScreen(
                                onSearchClick = {
                                    navController.navigate(Route.ProductSearch) {
                                        launchSingleTop = true
                                    }
                                },
                                onExploreAllClick = {
                                    navController.navigate(Route.ProductSearch) {
                                        launchSingleTop = true
                                    }
                                },
                                onCategoryClick = {
                                    navController.navigate(Route.ProductSearch) {
                                        launchSingleTop = true
                                    }
                                },
                                onBrandClick = {
                                    navController.navigate(Route.ProductSearch) {
                                        launchSingleTop = true
                                    }
                                },
                                onBannerClick = {
                                    navController.navigate(Route.ProductSearch) {
                                        launchSingleTop = true
                                    }
                                },
                                onProductClick = { product ->
                                    navController.navigate(Route.ProductDetail(product.uuid)) {
                                        launchSingleTop = true
                                    }
                                },
                                onNotificationsClick = {
                                    navController.navigate(Route.Notifications) {
                                        launchSingleTop = true
                                    }
                                },
                                onNavigateToAddressForm = {
                                    navController.navigate(Route.AddressForm()) {
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }
                        composable<Route.ProductDetail> {
                            ErrorView(
                                title = "Product Not Found",
                                subtitle = "The product you're looking for doesn't exist or is no longer available.",
                            )
                        }
                        composable<Route.ProductSearch> {
                            EmptyState(
                                title = "No Results Found",
                                subtitle = "Try a different keyword or filter.",
                                icon = Icons.Outlined.SearchOff,
                                lottieRawRes = null,
                            )
                        }
                        composable<Route.Cart> {
                            DestinationScreenEmpty(
                                title = stringResource(R.string.destination_cart_title),
                                subtitle = stringResource(R.string.destination_cart_subtitle),
                            ) {
                                EmptyState(
                                    title = "Your Cart is Empty",
                                    subtitle = "Add products to your cart to get started.",
                                )
                            }
                        }
                        composable<Route.Checkout> {
                            ErrorView(
                                title = "Checkout Unavailable",
                                subtitle = "We couldn't process your checkout. Please try again later.",
                                icon = Icons.Outlined.Payment,
                                lottieRawRes = null,
                            )
                        }
                        composable<Route.Orders> {
                            DestinationScreenEmpty(
                                title = stringResource(R.string.destination_orders_title),
                                subtitle = stringResource(R.string.destination_orders_subtitle),
                            ) {
                                EmptyState(
                                    title = "No Orders Yet",
                                    subtitle = "Your orders will appear here once you place one.",
                                    icon = Icons.AutoMirrored.Outlined.ReceiptLong,
                                    lottieRawRes = null,
                                )
                            }
                        }
                        composable<Route.OrderDetail> {
                            ErrorView(
                                title = "Order Not Found",
                                subtitle = "The order you're looking for doesn't exist.",
                            )
                        }
                        composable<Route.Wishlist> {
                            DestinationScreenEmpty(
                                title = stringResource(R.string.destination_wishlist_title),
                                subtitle = stringResource(R.string.destination_wishlist_subtitle),
                            ) {
                                EmptyState(
                                    title = "Wishlist is Empty",
                                    subtitle = "Save your favourite products to find them here.",
                                )
                            }
                        }
                        composable<Route.WriteReview> {
                            ErrorView(
                                title = "Review Unavailable",
                                subtitle = "Something went wrong while loading the review form.",
                                icon = Icons.Outlined.RateReview,
                                lottieRawRes = null,
                            )
                        }
                        composable<Route.AddressList> {
                            EmptyState(
                                title = "No Saved Addresses",
                                subtitle = "Add a delivery address to get started.",
                                icon = Icons.Outlined.LocationOn,
                                lottieRawRes = null,
                            )
                        }
                        composable<Route.AddressForm> {
                            ErrorView(
                                title = "Address Unavailable",
                                subtitle = "We couldn't load the address form. Please try again later.",
                                icon = Icons.Outlined.LocationOff,
                                lottieRawRes = null,
                            )
                        }
                        composable<Route.Wallet> {
                            DestinationScreenEmpty(
                                title = stringResource(R.string.destination_wallet_title),
                                subtitle = stringResource(R.string.destination_wallet_subtitle),
                            ) {
                                EmptyState(
                                    title = "No Transactions Yet",
                                    subtitle = "Your wallet transactions will appear here.",
                                    icon = Icons.Outlined.AccountBalanceWallet,
                                    lottieRawRes = null,
                                )
                            }
                        }
                        composable<Route.Notifications> {
                            DestinationScreenEmpty(
                                title = stringResource(R.string.destination_notifications_title),
                                subtitle = stringResource(R.string.destination_notifications_subtitle),
                            ) {
                                EmptyState(
                                    title = "No Notifications",
                                    subtitle = "You\'re all caught up!",
                                )
                            }
                        }
                        composable<Route.ChatList> {
                            DestinationScreenEmpty(
                                title = stringResource(R.string.destination_chat_title),
                                subtitle = stringResource(R.string.destination_chat_subtitle),
                            ) {
                                EmptyState(
                                    title = "No Conversations",
                                    subtitle = "Start a chat with support whenever you need help.",
                                    icon = Icons.Outlined.ChatBubbleOutline,
                                    lottieRawRes = null,
                                )
                            }
                        }
                        composable<Route.ChatDetail> {
                            EmptyState(
                                title = "No Messages",
                                subtitle = "Say hello to start the conversation.",
                                icon = Icons.Outlined.Forum,
                                lottieRawRes = null,
                            )
                        }
                        composable<Route.Profile> {
                            ProfileScreen()
                        }
                        composable<Route.EditProfile> {
                            ErrorView(
                                title = "Edit Unavailable",
                                subtitle = "Profile editing isn't available yet. Please try again later.",
                                icon = Icons.Outlined.EditOff,
                                lottieRawRes = null,
                            )
                        }
                    }
                }

                if (showBottomBar) {
                    FloatingBottomNavigation(
                        navController = navController,
                        modifier = Modifier.align(Alignment.BottomCenter),
                        visible = navBarVisible.value,
                    )
                }

                LiquefiedSnackBarHost(
                    hostState = snackbarHostState,
                    typeState = typedSnackBarState,
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }
        }
    }
}
