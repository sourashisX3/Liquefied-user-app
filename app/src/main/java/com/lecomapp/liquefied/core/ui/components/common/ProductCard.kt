package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lecomapp.liquefied.core.ui.components.display.RatingBar
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.features.catalog.domain.models.Product
import java.util.Locale

private val CardWidth = 140.dp
private val CardImageHeight = 160.dp
private val CardShape = RoundedCornerShape(AppCornerRadius.extraLarge)

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .width(CardWidth)
            .clip(CardShape)
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() }),
    ) {
        if (product.primaryImage != null) {
            AsyncImage(
                model = product.primaryImage,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CardImageHeight),
            )
        } else {
            ShimmerSkeleton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CardImageHeight),
                shape = CardShape,
            )
        }
        Spacer(modifier = Modifier.height(AppSpacing.sm))
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
            Row {
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
        Spacer(modifier = Modifier.height(AppSpacing.xs))
        Text(
            text = String.format(Locale.US, "₹%.2f", product.minVariantPrice ?: product.basePrice),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun SkeletonProductCard(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.width(CardWidth)) {
        ShimmerSkeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(CardImageHeight),
            shape = CardShape,
        )
    }
}