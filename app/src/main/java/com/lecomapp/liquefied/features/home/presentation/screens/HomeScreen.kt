package com.lecomapp.liquefied.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.common.BannerCarousel
import com.lecomapp.liquefied.core.ui.components.common.BrandRow
import com.lecomapp.liquefied.core.ui.components.common.CategoryRow
import com.lecomapp.liquefied.core.ui.components.common.SectionHeader
import com.lecomapp.liquefied.core.ui.components.feedback.ErrorView
import com.lecomapp.liquefied.core.ui.components.navigation.FloatingNavigationDefaults
import com.lecomapp.liquefied.core.ui.components.navigation.NavigationCapsuleDefaults
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LiquefiedTheme
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.catalog.domain.models.Brand
import com.lecomapp.liquefied.features.catalog.domain.models.Category
import com.lecomapp.liquefied.features.catalog.domain.models.Product
import com.lecomapp.liquefied.features.home.domain.models.Banner
import com.lecomapp.liquefied.features.home.domain.models.HomeData
import com.lecomapp.liquefied.features.home.presentation.components.HomeHeader
import com.lecomapp.liquefied.features.home.presentation.components.HomeProductRail
import com.lecomapp.liquefied.features.home.presentation.components.HomeSkeleton
import com.lecomapp.liquefied.features.home.presentation.viewmodels.HomeViewModel
import com.lecomapp.liquefied.features.home.presentation.viewmodels.states.HomeAction
import com.lecomapp.liquefied.features.home.presentation.viewmodels.states.HomeState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit = {},
    onExploreAllClick: () -> Unit = {},
    onCategoryClick: (Category) -> Unit = {},
    onBrandClick: (Brand) -> Unit = {},
    onBannerClick: (Banner) -> Unit = {},
    onProductClick: (Product) -> Unit = {},
    onNotificationsClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreenContent(
        state = state,
        onAction = viewModel::onAction,
        onSearchClick = onSearchClick,
        onExploreAllClick = onExploreAllClick,
        onCategoryClick = onCategoryClick,
        onBrandClick = onBrandClick,
        onBannerClick = onBannerClick,
        onProductClick = onProductClick,
        onNotificationsClick = onNotificationsClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onSearchClick: () -> Unit = {},
    onExploreAllClick: () -> Unit = {},
    onCategoryClick: (Category) -> Unit = {},
    onBrandClick: (Brand) -> Unit = {},
    onBannerClick: (Banner) -> Unit = {},
    onProductClick: (Product) -> Unit = {},
    onNotificationsClick: () -> Unit = {},
) {
    val safeTopPadding = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()
    val pullToRefreshState = rememberPullToRefreshState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        when {
            state.homeData != null -> {
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    onRefresh = { onAction(HomeAction.Refresh) },
                    state = pullToRefreshState,
                    modifier = Modifier.fillMaxSize(),
                    indicator = {
                        PullToRefreshDefaults.Indicator(
                            state = pullToRefreshState,
                            isRefreshing = state.isRefreshing,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = safeTopPadding),
                        )
                    },
                ) {
                    HomeContent(
                        data = state.homeData,
                        onSearchClick = onSearchClick,
                        onExploreAllClick = onExploreAllClick,
                        onCategoryClick = onCategoryClick,
                        onBrandClick = onBrandClick,
                        onBannerClick = onBannerClick,
                        onProductClick = onProductClick,
                        onNotificationsClick = onNotificationsClick,
                    )
                }
            }
            state.isLoading -> {
                HomeSkeleton(
                    onSearchClick = onSearchClick,
                    onExploreAllClick = onExploreAllClick,
                    onNotificationsClick = onNotificationsClick,
                )
            }
            else -> {
                ErrorView(
                    title = stringResource(R.string.home_error_title),
                    subtitle = stringResource(R.string.home_error_subtitle),
                    onRetry = { onAction(HomeAction.Retry) },
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    data: HomeData,
    onSearchClick: () -> Unit,
    onExploreAllClick: () -> Unit,
    onCategoryClick: (Category) -> Unit,
    onBrandClick: (Brand) -> Unit,
    onBannerClick: (Banner) -> Unit,
    onProductClick: (Product) -> Unit,
    onNotificationsClick: () -> Unit,
) {
    val tagline = UiText.StringResourceId(R.string.home_tagline)
    val chooseYourSpirit = UiText.StringResourceId(R.string.home_choose_your_spirit)
    val shopByBrand = UiText.StringResourceId(R.string.home_shop_by_brand)
    val newArrivalsTitle = UiText.StringResourceId(R.string.home_new_arrivals)
    val featuredTitle = UiText.StringResourceId(R.string.home_featured)
    val bestSellersTitle = UiText.StringResourceId(R.string.home_best_sellers)
    val trendingTitle = UiText.StringResourceId(R.string.home_trending)
    val dealsTitle = UiText.StringResourceId(R.string.home_deals)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = NavigationCapsuleDefaults.height + FloatingNavigationDefaults.bottomPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.xl),
    ) {
        item(key = "brand_header") {
            HomeHeader(
                tagline = tagline,
                walletBalance = data.walletBalance,
                unreadNotificationCount = data.unreadNotificationCount,
                onSearchClick = onSearchClick,
                onNotificationsClick = onNotificationsClick,
            )
        }

        if (data.banners.isNotEmpty()) {
            item(key = "banners") {
                BannerCarousel(
                    banners = data.banners,
                    onBannerClick = onBannerClick,
                )
            }
        }

        if (data.categories.isNotEmpty()) {
            item(key = "categories") {
                Column {
                    SectionHeader(
                        title = chooseYourSpirit.asString(),
                        onAction = onExploreAllClick,
                        modifier = Modifier.padding(horizontal = AppSpacing.lg),
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    CategoryRow(
                        categories = data.categories,
                        onCategoryClick = onCategoryClick,
                    )
                }
            }
        }

        if (data.brands.isNotEmpty()) {
            item(key = "brands") {
                Column {
                    SectionHeader(
                        title = shopByBrand.asString(),
                        onAction = onExploreAllClick,
                        modifier = Modifier.padding(horizontal = AppSpacing.lg),
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    BrandRow(
                        brands = data.brands,
                        onBrandClick = onBrandClick,
                    )
                }
            }
        }

        HomeProductRail(
            key = "new_arrivals",
            title = newArrivalsTitle,
            products = data.newArrivals,
            onProductClick = onProductClick,
            onExploreAllClick = onExploreAllClick,
        )
        HomeProductRail(
            key = "featured",
            title = featuredTitle,
            products = data.featuredProducts,
            onProductClick = onProductClick,
            onExploreAllClick = onExploreAllClick,
        )
        HomeProductRail(
            key = "best_sellers",
            title = bestSellersTitle,
            products = data.bestSellers,
            onProductClick = onProductClick,
            onExploreAllClick = onExploreAllClick,
        )
        HomeProductRail(
            key = "trending",
            title = trendingTitle,
            products = data.trending,
            onProductClick = onProductClick,
            onExploreAllClick = onExploreAllClick,
        )
        HomeProductRail(
            key = "deals",
            title = dealsTitle,
            products = data.deals,
            onProductClick = onProductClick,
            onExploreAllClick = onExploreAllClick,
        )

        item(key = "bottom_spacer") {
            Spacer(modifier = Modifier.height(AppSpacing.sm))
        }
    }
}

private val previewHomeData = HomeData(
    categories = listOf(
        Category(uuid = "1", name = "Whisky", slug = "whisky", imageUrl = null, productCount = 10),
        Category(uuid = "2", name = "Vodka", slug = "vodka", imageUrl = null, productCount = 8),
        Category(uuid = "3", name = "Rum", slug = "rum", imageUrl = null, productCount = 6),
        Category(uuid = "4", name = "Gin", slug = "gin", imageUrl = null, productCount = 5),
    ),
    brands = listOf(
        Brand(uuid = "1", name = "Jack Daniel's", slug = "jack-daniels", logoUrl = null, productCount = 4),
        Brand(uuid = "2", name = "Absolut", slug = "absolut", logoUrl = null, productCount = 6),
        Brand(uuid = "3", name = "Bacardi", slug = "bacardi", logoUrl = null, productCount = 5),
    ),
    banners = listOf(
        Banner(uuid = "1", title = "Festive Season Sale", subtitle = "Up to 30% off premium spirits", imageUrl = null, linkType = null, linkValue = null),
        Banner(uuid = "2", title = "New Collection", subtitle = "Discover this month's arrivals", imageUrl = null, linkType = null, linkValue = null),
    ),
    newArrivals = listOf(
        Product(uuid = "1", name = "Jack Daniel's Old No. 7", slug = "jack-daniels-old-no-7", shortDescription = null, basePrice = 32.99, minVariantPrice = 32.99, maxVariantPrice = null, isFeatured = false, primaryImage = null, categoryName = "Whisky", brandName = "Jack Daniel's", brandLogoUrl = null, averageRating = 4.5, ratingCount = 120),
        Product(uuid = "2", name = "Absolut Vodka", slug = "absolut-vodka", shortDescription = null, basePrice = 24.99, minVariantPrice = 24.99, maxVariantPrice = null, isFeatured = false, primaryImage = null, categoryName = "Vodka", brandName = "Absolut", brandLogoUrl = null, averageRating = 4.2, ratingCount = 85),
        Product(uuid = "3", name = "Bacardi Superior", slug = "bacardi-superior", shortDescription = null, basePrice = 19.99, minVariantPrice = 19.99, maxVariantPrice = null, isFeatured = false, primaryImage = null, categoryName = "Rum", brandName = "Bacardi", brandLogoUrl = null, averageRating = 4.7, ratingCount = 210),
    ),
    featuredProducts = listOf(
        Product(uuid = "4", name = "Grey Goose", slug = "grey-goose", shortDescription = null, basePrice = 45.0, minVariantPrice = 45.0, maxVariantPrice = null, isFeatured = true, primaryImage = null, categoryName = "Vodka", brandName = "Grey Goose", brandLogoUrl = null, averageRating = 4.8, ratingCount = 320),
        Product(uuid = "5", name = "Johnnie Walker Blue", slug = "johnnie-walker-blue", shortDescription = null, basePrice = 210.0, minVariantPrice = 210.0, maxVariantPrice = null, isFeatured = true, primaryImage = null, categoryName = "Whisky", brandName = "Johnnie Walker", brandLogoUrl = null, averageRating = 4.9, ratingCount = 540),
    ),
    bestSellers = listOf(
        Product(uuid = "6", name = "Captain Morgan", slug = "captain-morgan", shortDescription = null, basePrice = 22.99, minVariantPrice = 22.99, maxVariantPrice = null, isFeatured = false, primaryImage = null, categoryName = "Rum", brandName = "Captain Morgan", brandLogoUrl = null, averageRating = 4.3, ratingCount = 420),
    ),
    trending = emptyList(),
    deals = emptyList(),
    unreadNotificationCount = 3,
    walletBalance = 150.59,
)

@Preview(name = "Home - Light", showBackground = true)
@Composable
private fun HomeScreenLightPreview() {
    LiquefiedTheme {
        HomeScreenContent(
            state = HomeState(homeData = previewHomeData),
            onAction = {},
        )
    }
}

@Preview(name = "Home - Dark", showBackground = true)
@Composable
private fun HomeScreenDarkPreview() {
    LiquefiedTheme(darkTheme = true) {
        HomeScreenContent(
            state = HomeState(homeData = previewHomeData),
            onAction = {},
        )
    }
}

@Preview(name = "Home - Skeleton", showBackground = true)
@Composable
private fun HomeScreenSkeletonPreview() {
    LiquefiedTheme {
        HomeScreenContent(
            state = HomeState(isLoading = true),
            onAction = {},
        )
    }
}