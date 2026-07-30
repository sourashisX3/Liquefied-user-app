package com.lecomapp.liquefied.features.auth.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun LoginHeader(
    headerAlpha: Float,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.login_welcome_back),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        modifier = modifier.alpha(headerAlpha),
    )

    Spacer(modifier = Modifier.height(AppSpacing.sm))

    Text(
        text = stringResource(R.string.login_sign_in_continue),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = Modifier.alpha(headerAlpha),
    )
}
