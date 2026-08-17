package com.lecomapp.liquefied.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.lecomapp.liquefied.core.ui.components.common.ProductCard
import com.lecomapp.liquefied.core.ui.components.common.ProductCardDefaults
import com.lecomapp.liquefied.core.ui.components.common.SectionHeader
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.catalog.domain.models.Product

fun LazyListScope.HomeProductRail(
    key: String,
    title: UiText,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onExploreAllClick: () -> Unit,
) {
    if (products.isEmpty()) return
    item(key = key) {
        Column {
            SectionHeader(
                title = title.asString(),
                onAction = onExploreAllClick,
                modifier = Modifier.padding(horizontal = AppSpacing.lg),
            )
            Spacer(modifier = Modifier.height(AppSpacing.sm))
            LazyRow(
                modifier = Modifier.height(ProductCardDefaults.totalHeight),
                contentPadding = PaddingValues(horizontal = AppSpacing.lg),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
            ) {
                items(products, key = { it.slug }) { product ->
                    ProductCard(
                        product = product,
                        onClick = { onProductClick(product) },
                    )
                }
            }
        }
    }
}