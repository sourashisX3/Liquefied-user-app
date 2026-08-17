package com.lecomapp.liquefied.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.common.ProductCardDefaults
import com.lecomapp.liquefied.core.ui.components.common.SectionHeader
import com.lecomapp.liquefied.core.ui.components.common.SkeletonBannerCarousel
import com.lecomapp.liquefied.core.ui.components.common.SkeletonBrandItem
import com.lecomapp.liquefied.core.ui.components.common.SkeletonCategoryItem
import com.lecomapp.liquefied.core.ui.components.common.SkeletonProductCard
import com.lecomapp.liquefied.core.ui.components.navigation.FloatingNavigationDefaults
import com.lecomapp.liquefied.core.ui.components.navigation.NavigationCapsuleDefaults
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.utils.UiText

@Composable
fun HomeSkeleton(
    onSearchClick: () -> Unit,
    onExploreAllClick: () -> Unit,
    onNotificationsClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = NavigationCapsuleDefaults.height + FloatingNavigationDefaults.bottomPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.xl),
    ) {
        item(key = "brand_header") {
            HomeHeader(
                tagline = UiText.StringResourceId(R.string.home_tagline),
                walletBalance = null,
                unreadNotificationCount = 0,
                onSearchClick = onSearchClick,
                onNotificationsClick = onNotificationsClick,
            )
        }
        item(key = "banners") {
            SkeletonBannerCarousel()
        }
        item(key = "categories") {
            Column {
                SectionHeader(
                    title = stringResource(R.string.home_choose_your_spirit),
                    onAction = onExploreAllClick,
                    modifier = Modifier.padding(horizontal = AppSpacing.lg),
                )
                Spacer(modifier = Modifier.height(AppSpacing.sm))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppSpacing.lg),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
                ) {
                    repeat(4) {
                        item(key = "skeleton_category_$it") {
                            SkeletonCategoryItem()
                        }
                    }
                }
            }
        }
        item(key = "brands") {
            Column {
                SectionHeader(
                    title = stringResource(R.string.home_shop_by_brand),
                    onAction = onExploreAllClick,
                    modifier = Modifier.padding(horizontal = AppSpacing.lg),
                )
                Spacer(modifier = Modifier.height(AppSpacing.sm))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = AppSpacing.lg),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.md),
                ) {
                    repeat(4) {
                        item(key = "skeleton_brand_$it") {
                            SkeletonBrandItem()
                        }
                    }
                }
            }
        }
        item(key = "new_arrivals") {
            Column {
                SectionHeader(
                    title = stringResource(R.string.home_new_arrivals),
                    onAction = onExploreAllClick,
                    modifier = Modifier.padding(horizontal = AppSpacing.lg),
                )
                Spacer(modifier = Modifier.height(AppSpacing.sm))
                LazyRow(
                    modifier = Modifier.height(ProductCardDefaults.totalHeight),
                    contentPadding = PaddingValues(horizontal = AppSpacing.lg),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
                ) {
                    repeat(3) {
                        item(key = "skeleton_product_$it") {
                            SkeletonProductCard()
                        }
                    }
                }
            }
        }
    }
}