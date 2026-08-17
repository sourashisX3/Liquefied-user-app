package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun AppLogoSection(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    tagline: String? = null,
    appNameAlpha: Float = 1f,
    appNameScale: Float = 1f,
    taglineAlpha: Float = 1f,
    taglineOffsetY: Float = 0f,
    appNameStyle: TextStyle? = null,
    appNameMaxLines: Int? = null,
    appNameSoftWrap: Boolean? = null,
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = appNameStyle ?: MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = if (horizontalAlignment == Alignment.CenterHorizontally) TextAlign.Center else TextAlign.Start,
            maxLines = appNameMaxLines ?: Int.MAX_VALUE,
            softWrap = appNameSoftWrap ?: true,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .scale(appNameScale)
                .alpha(appNameAlpha),
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        Text(
            text = tagline ?: stringResource(R.string.splash_tagline),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
            textAlign = if (horizontalAlignment == Alignment.CenterHorizontally) TextAlign.Center else TextAlign.Start,
            modifier = Modifier
                .alpha(taglineAlpha)
                .offset(y = (40 * taglineOffsetY).dp),
        )
    }
}