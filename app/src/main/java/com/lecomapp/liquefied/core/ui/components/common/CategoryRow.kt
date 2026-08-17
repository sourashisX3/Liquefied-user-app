package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.lecomapp.liquefied.core.ui.theme.rememberImagePlaceholderColor
import com.lecomapp.liquefied.features.catalog.domain.models.Category

private val CategoryCircleSize: Dp = 80.dp
private val CategoryItemHeight: Dp = 132.dp

@Composable
fun CategoryRow(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    onCategoryClick: ((Category) -> Unit)? = null,
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val itemWidth = (this.maxWidth - AppSpacing.lg * 2 - AppSpacing.sm * 3) / 4
        LazyRow(
            contentPadding = PaddingValues(horizontal = AppSpacing.lg),
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
        ) {
            items(categories, key = { it.slug }) { category ->
                CategoryItem(
                    category = category,
                    onClick = { onCategoryClick?.invoke(category) },
                    modifier = Modifier.width(itemWidth),
                )
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: Category,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .height(CategoryItemHeight)
            .clip(RoundedCornerShape(AppCornerRadius.medium))
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (category.imageUrl != null) {
            AsyncImage(
                model = category.imageUrl,
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(rememberImagePlaceholderColor()),
                error = ColorPainter(rememberImagePlaceholderColor()),
                modifier = Modifier
                    .size(CategoryCircleSize)
                    .clip(CircleShape),
            )
        } else {
            Box(
                modifier = Modifier
                    .size(CategoryCircleSize)
                    .clip(CircleShape)
                    .background(rememberImagePlaceholderColor()),
            )
        }
        Spacer(modifier = Modifier.height(AppSpacing.sm))
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SkeletonCategoryItem(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.height(CategoryItemHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShimmerSkeleton(
            modifier = Modifier.size(CategoryCircleSize),
            shape = CircleShape,
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