package com.lecomapp.liquefied.features.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.components.common.AnimatedDiamonds
import com.lecomapp.liquefied.core.ui.components.common.AppLogoSection
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun AuthScreenLayout(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            AnimatedDiamonds(modifier = Modifier.matchParentSize())
            AppLogoSection(
                appNameAlpha = 1f,
                appNameScale = 1f,
                taglineAlpha = 1f,
                taglineOffsetY = 0f,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
