package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.AnimationTokens
import com.lecomapp.liquefied.core.ui.theme.LocalImageOverlayColors
import com.lecomapp.liquefied.core.ui.theme.rememberImagePlaceholderColor
import com.lecomapp.liquefied.features.home.domain.models.Banner
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.yield

private val BannerHeight = 170.dp
private val BannerShape = RoundedCornerShape(AppCornerRadius.extraLarge)
private const val BannerScrimHeightFraction = 0.65f

@Composable
fun BannerCarousel(
    banners: List<Banner>,
    modifier: Modifier = Modifier,
    onBannerClick: ((Banner) -> Unit)? = null,
) {
    if (banners.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { banners.size })
    val autoAdvance = banners.size > 1

    LaunchedEffect(banners.size) {
        if (!autoAdvance) return@LaunchedEffect
        while (isActive) {
            yield()
            delay(AnimationTokens.Duration.CarouselDelay.toLong())
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % banners.size)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = AppSpacing.lg),
            pageSpacing = AppSpacing.sm,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            val banner = banners[page]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(BannerHeight)
                    .clip(BannerShape)
                    .clickable(enabled = onBannerClick != null) { onBannerClick?.invoke(banner) },
            ) {
                if (banner.imageUrl != null) {
                    SubcomposeAsyncImage(
                        model = banner.imageUrl,
                        contentDescription = banner.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        when (painter.state) {
                            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                            else -> Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(rememberImagePlaceholderColor())
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(rememberImagePlaceholderColor()),
                    )
                }
                // Bottom gradient scrim keeps the image visible while guaranteeing text
                // readability over any image brightness, in light and dark mode.
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(BannerScrimHeightFraction)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    LocalImageOverlayColors.current.scrim.copy(alpha = 0f),
                                    LocalImageOverlayColors.current.scrim,
                                ),
                            ),
                        ),
                )
                if (banner.title.isNotBlank() || banner.subtitle?.isNotBlank() == true) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(AppSpacing.md),
                    ) {
                        if (banner.title.isNotBlank()) {
                            Text(
                                text = banner.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = LocalImageOverlayColors.current.onScrim,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        if (banner.subtitle?.isNotBlank() == true) {
                            Text(
                                text = banner.subtitle.orEmpty(),
                                style = MaterialTheme.typography.bodySmall,
                                color = LocalImageOverlayColors.current.onScrim.copy(alpha = 0.85f),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }

        if (autoAdvance) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = AppSpacing.sm),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(banners.size) { index ->
                    val selected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .width(if (selected) 18.dp else 6.dp)
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(
                                if (selected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            ),
                    )
                }
            }
        }
    }
}

@Composable
fun SkeletonBannerCarousel(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        ShimmerSkeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(BannerHeight)
                .padding(horizontal = AppSpacing.lg),
            shape = BannerShape,
        )
    }
}