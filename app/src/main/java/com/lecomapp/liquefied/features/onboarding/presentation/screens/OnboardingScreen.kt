package com.lecomapp.liquefied.features.onboarding.presentation.screens

import androidx.annotation.RawRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.buttons.AppButton
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LiquefiedTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private data class OnboardingPage(
    @RawRes val lottieRes: Int,
    val titleRes: Int,
    val subtitleRes: Int,
)

private val onboardingPages = listOf(
    OnboardingPage(
        lottieRes = R.raw.girl_onboarding,
        titleRes = R.string.onboarding_title_1,
        subtitleRes = R.string.onboarding_subtitle_1,
    ),
    OnboardingPage(
        lottieRes = R.raw.flying_paper_plane,
        titleRes = R.string.onboarding_title_2,
        subtitleRes = R.string.onboarding_subtitle_2,
    ),
)

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == onboardingPages.lastIndex

    val contentAlpha = remember { Animatable(0f) }
    val contentOffsetY = remember { Animatable(0.2f) }

    LaunchedEffect(Unit) {
        coroutineScope {
            launch {
                contentAlpha.animateTo(1f, animationSpec = tween(durationMillis = 600))
            }
            launch {
                contentOffsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium,
                    ),
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(contentAlpha.value)
                .offset(y = (32 * contentOffsetY.value).dp)
                .padding(horizontal = AppSpacing.lg, vertical = AppSpacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = onFinish,
                    modifier = Modifier.align(Alignment.TopEnd),
                ) {
                    Text(
                        text = stringResource(R.string.onboarding_skip),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) { page ->
                OnboardingPageContent(page = onboardingPages[page])
            }

            Row(
                modifier = Modifier.padding(vertical = AppSpacing.lg),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs),
            ) {
                repeat(onboardingPages.size) { index ->
                    val active = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (active) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (active) {
                                    MaterialTheme.colorScheme.secondary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                                },
                            ),
                    )
                }
            }

            AppButton(
                onClick = {
                    if (isLastPage) {
                        // TODO(uncomment-on-first-launch-only): persist "onboarding_seen"
                        // flag (SharedPreferences/DataStore) before finishing.
                        onFinish()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                text = stringResource(
                    if (isLastPage) R.string.onboarding_get_started else R.string.onboarding_next,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(page.lottieRes),
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = Int.MAX_VALUE,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppSpacing.xxl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(280.dp),
        )

        Text(
            text = stringResource(page.titleRes),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )

        Text(
            text = stringResource(page.subtitleRes),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = AppSpacing.md, top = AppSpacing.sm, end = AppSpacing.md),
        )
    }
}

@Preview(name = "Onboarding - Light", showBackground = true)
@Composable
private fun OnboardingScreenLightPreview() {
    LiquefiedTheme {
        OnboardingScreen(onFinish = {})
    }
}

@Preview(name = "Onboarding - Dark", showBackground = true)
@Composable
private fun OnboardingScreenDarkPreview() {
    LiquefiedTheme(darkTheme = true) {
        OnboardingScreen(onFinish = {})
    }
}
