package com.lecomapp.liquefied.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.feedback.ErrorView
import com.lecomapp.liquefied.core.ui.components.feedback.LoadingIndicator
import com.lecomapp.liquefied.core.ui.components.navigation.FloatingNavigationDefaults
import com.lecomapp.liquefied.core.ui.components.navigation.NavigationCapsuleDefaults
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LiquefiedTheme
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.home.domain.models.Brand
import com.lecomapp.liquefied.features.home.domain.models.Category
import com.lecomapp.liquefied.features.home.domain.models.HomeData
import com.lecomapp.liquefied.features.home.domain.models.Product
import com.lecomapp.liquefied.features.home.presentation.components.CategoryChips
import com.lecomapp.liquefied.features.home.presentation.components.HomeHeader
import com.lecomapp.liquefied.features.home.presentation.components.ProductCard
import com.lecomapp.liquefied.features.home.presentation.components.SectionHeader
import com.lecomapp.liquefied.features.home.presentation.viewmodels.HomeViewModel
import com.lecomapp.liquefied.features.home.presentation.viewmodels.states.HomeAction
import com.lecomapp.liquefied.features.home.presentation.viewmodels.states.HomeState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreenContent(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun HomeScreenContent(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        when {
            state.isLoading -> {
                LoadingIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> {
                ErrorView(
                    title = stringResource(R.string.home_error_title),
                    subtitle = stringResource(R.string.home_error_subtitle),
                    onRetry = { onAction(HomeAction.Retry) },
                )
            }
            state.homeData != null -> {
                HomeContent(data = state.homeData)
            }
        }
    }
}

@Composable
private fun HomeContent(data: HomeData) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = AppSpacing.lg,
            end = AppSpacing.lg,
            top = AppSpacing.md,
            bottom = NavigationCapsuleDefaults.height + FloatingNavigationDefaults.bottomPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.lg),
    ) {
        item(key = "header") {
            HomeHeader()
        }
        if (data.categories.isNotEmpty()) {
            item(key = "categories") {
                Column {
                    SectionHeader(title = stringResource(R.string.home_categories))
                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    CategoryChips(categories = data.categories)
                }
            }
        }
        if (data.brands.isNotEmpty()) {
            item(key = "brands") {
                Column {
                    SectionHeader(title = stringResource(R.string.home_brands))
                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    BrandRow(brands = data.brands)
                }
            }
        }
        if (data.newArrivals.isNotEmpty()) {
            item(key = "new_arrivals") {
                Column {
                    SectionHeader(title = stringResource(R.string.home_new_arrivals))
                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
                    ) {
                        items(data.newArrivals, key = { it.slug }) { product ->
                            ProductCard(
                                product = product,
                                modifier = Modifier.width(160.dp).height(240.dp),
                            )
                        }
                    }
                }
            }
        }
        if (data.featuredProducts.isNotEmpty()) {
            item(key = "featured") {
                Column {
                    SectionHeader(title = stringResource(R.string.home_featured))
                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.sm)) {
                        data.featuredProducts.chunked(2).forEach { rowProducts ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
                            ) {
                                rowProducts.forEach { product ->
                                    ProductCard(
                                        product = product,
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BrandRow(brands: List<Brand>) {
LazyRow(
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
    ) {
        items(brands, key = { it.slug }) { brand ->
            Column(
                modifier = Modifier.width(88.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .width(72.dp)
                        .height(72.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = brand.logoUrl,
                        contentDescription = brand.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize(),
                    )
                }
                Spacer(modifier = Modifier.height(AppSpacing.xs))
                Text(
                    text = brand.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
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
        Brand(uuid = "2", name = "Absolut", slug = "absolut", logoUrl = null, productCount = 3),
        Brand(uuid = "3", name = "Bacardi", slug = "bacardi", logoUrl = null, productCount = 2),
    ),
    newArrivals = listOf(
        Product(uuid = "1", name = "Jack Daniel's Old No. 7", slug = "jack-daniels-old-no-7", shortDescription = null, basePrice = 32.99, minVariantPrice = 32.99, maxVariantPrice = null, isFeatured = false, primaryImage = null, categoryName = "Whisky", brandName = "Jack Daniel's", brandLogoUrl = null, averageRating = 4.5, ratingCount = 120),
        Product(uuid = "2", name = "Absolut Vodka", slug = "absolut-vodka", shortDescription = null, basePrice = 24.99, minVariantPrice = 24.99, maxVariantPrice = null, isFeatured = false, primaryImage = null, categoryName = "Vodka", brandName = "Absolut", brandLogoUrl = null, averageRating = 4.2, ratingCount = 85),
    ),
    featuredProducts = listOf(
        Product(uuid = "3", name = "Bacardi Superior", slug = "bacardi-superior", shortDescription = null, basePrice = 19.99, minVariantPrice = 19.99, maxVariantPrice = null, isFeatured = true, primaryImage = null, categoryName = "Rum", brandName = "Bacardi", brandLogoUrl = null, averageRating = 4.7, ratingCount = 210),
        Product(uuid = "4", name = "Hendrick's Gin", slug = "hendricks-gin", shortDescription = null, basePrice = 39.99, minVariantPrice = 39.99, maxVariantPrice = null, isFeatured = true, primaryImage = null, categoryName = "Gin", brandName = "Hendrick's", brandLogoUrl = null, averageRating = 4.8, ratingCount = 95),
    ),
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
