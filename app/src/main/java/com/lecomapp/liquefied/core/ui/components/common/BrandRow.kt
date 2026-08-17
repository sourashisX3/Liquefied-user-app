package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.features.catalog.domain.models.Brand

private val BrandLogoSize: Dp = 64.dp
private val BrandShape = RoundedCornerShape(AppCornerRadius.large)
private val BrandItemHeight: Dp = 116.dp

@Composable
fun BrandRow(
    brands: List<Brand>,
    modifier: Modifier = Modifier,
    onBrandClick: ((Brand) -> Unit)? = null,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = AppSpacing.lg),
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.md),
    ) {
        items(brands, key = { it.slug }) { brand ->
            BrandItem(
                brand = brand,
                onClick = { onBrandClick?.invoke(brand) },
            )
        }
    }
}

@Composable
fun SkeletonBrandItem(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(88.dp)
            .height(BrandItemHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShimmerSkeleton(
            modifier = Modifier.size(BrandLogoSize),
            shape = BrandShape,
        )
        Spacer(modifier = Modifier.height(AppSpacing.sm))
        ShimmerSkeleton(
            modifier = Modifier
                .width(56.dp)
                .height(18.dp),
            shape = RoundedCornerShape(AppCornerRadius.small),
        )
    }
}

@Composable
private fun BrandItem(
    brand: Brand,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(88.dp)
            .height(BrandItemHeight)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (brand.logoUrl != null) {
            AsyncImage(
                model = brand.logoUrl,
                contentDescription = brand.name,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .size(BrandLogoSize)
                    .clip(BrandShape),
            )
        } else {
            Box(
                modifier = Modifier
                    .size(BrandLogoSize)
                    .clip(BrandShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            )
        }
        Spacer(modifier = Modifier.height(AppSpacing.sm))
        Text(
            text = brand.name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}