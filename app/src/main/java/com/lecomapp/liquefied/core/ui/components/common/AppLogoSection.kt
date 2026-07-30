package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun AppLogoSection(
    modifier: Modifier = Modifier,
    appNameAlpha: Float = 1f,
    appNameScale: Float = 1f,
    taglineAlpha: Float = 1f,
    taglineOffsetY: Float = 0f,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = AppSpacing.lg),
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .scale(appNameScale)
                .alpha(appNameAlpha),
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        Text(
            text = stringResource(R.string.splash_tagline),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .alpha(taglineAlpha)
                .offset(y = (40 * taglineOffsetY).dp),
        )
    }
}
