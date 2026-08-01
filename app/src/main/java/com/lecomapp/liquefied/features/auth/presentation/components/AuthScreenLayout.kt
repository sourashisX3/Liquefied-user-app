package com.lecomapp.liquefied.features.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.components.common.AnimatedDiamonds
import com.lecomapp.liquefied.core.ui.components.common.AppLogoSection
import com.lecomapp.liquefied.core.ui.components.common.LanguageSelector
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

private const val LOGO_SECTION_FRACTION = 0.286f

@Composable
fun AuthScreenLayout(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        val baseTopHeightPx = with(density) { maxHeight.toPx() } * LOGO_SECTION_FRACTION
        val collapsePx = scrollState.value.toFloat().coerceIn(0f, baseTopHeightPx)
        val collapseFraction = if (baseTopHeightPx > 0f) collapsePx / baseTopHeightPx else 1f

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(density) { (baseTopHeightPx - collapsePx).toDp() })
                    .alpha(1f - collapseFraction),
                contentAlignment = Alignment.Center,
            ) {
                AnimatedDiamonds(modifier = Modifier.matchParentSize())
                AppLogoSection(
                    modifier = Modifier.offset(y = with(density) { (-collapsePx / 2f).toDp() }),
                )
                LanguageSelector(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = AppSpacing.md, end = AppSpacing.md),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(
                            start = AppSpacing.lg,
                            end = AppSpacing.lg,
                            top = AppSpacing.xl,
                            bottom = AppSpacing.xl,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = content,
                )
            }
        }
    }
}
