package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lecomapp.liquefied.core.ui.components.display.RatingBar
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.features.catalog.domain.models.Product
import java.util.Locale

object ProductCardDefaults {
    val width: Dp = 140.dp
    val imageHeight: Dp = 140.dp
    val totalHeight: Dp = 248.dp
    val shape = RoundedCornerShape(AppCornerRadius.extraLarge)
}

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .width(ProductCardDefaults.width)
            .height(ProductCardDefaults.totalHeight)
            .clip(ProductCardDefaults.shape)
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() }),
    ) {
        ProductImagePlaceholder(
            imageUrl = product.primaryImage,
            contentDescription = product.name,
            shape = ProductCardDefaults.shape,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    start = AppSpacing.xs,
                    end = AppSpacing.xs,
                    top = AppSpacing.xs,
                    bottom = AppSpacing.xs,
                ),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                if (product.brandName.isNotBlank()) {
                    Text(
                        text = product.brandName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.xxs))
                }
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (product.ratingCount > 0) {
                    Spacer(modifier = Modifier.height(AppSpacing.xxs))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RatingBar(
                            rating = product.averageRating,
                            starSize = 14.dp,
                        )
                        Spacer(modifier = Modifier.width(AppSpacing.xxs))
                        Text(
                            text = "(${product.ratingCount})",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
            Text(
                text = String.format(Locale.US, "₹%.2f", product.minVariantPrice ?: product.basePrice),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun SkeletonProductCard(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.width(ProductCardDefaults.width).height(ProductCardDefaults.totalHeight)) {
        ShimmerSkeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(ProductCardDefaults.imageHeight),
            shape = ProductCardDefaults.shape,
        )
        Spacer(modifier = Modifier.height(AppSpacing.sm))
        Column(
            modifier = Modifier.padding(horizontal = AppSpacing.xs),
            verticalArrangement = Arrangement.spacedBy(AppSpacing.xs),
        ) {
            ShimmerSkeleton(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(12.dp),
                shape = RoundedCornerShape(AppCornerRadius.small),
            )
            ShimmerSkeleton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp),
                shape = RoundedCornerShape(AppCornerRadius.small),
            )
            ShimmerSkeleton(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(14.dp),
                shape = RoundedCornerShape(AppCornerRadius.small),
            )
        }
    }
}

@Composable
internal fun ProductImagePlaceholder(
    imageUrl: String?,
    contentDescription: String?,
    shape: androidx.compose.ui.graphics.Shape,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(ProductCardDefaults.imageHeight)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}