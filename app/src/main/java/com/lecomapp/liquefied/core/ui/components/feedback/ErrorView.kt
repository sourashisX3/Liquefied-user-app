package com.lecomapp.liquefied.core.ui.components.feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.buttons.AppButton
import com.lecomapp.liquefied.core.ui.components.buttons.ButtonVariant
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    title: String = "Something went wrong",
    subtitle: String? = null,
    icon: ImageVector? = null,
    lottieRawRes: Int? = R.raw.not_found_error,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppSpacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (lottieRawRes != null) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(lottieRawRes),
            )
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = 1,
            )
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(180.dp),
            )
        } else if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error,
            )
        }
        Spacer(modifier = Modifier.height(AppSpacing.md))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(AppSpacing.xs))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(AppSpacing.xl))
            AppButton(
                text = "Retry",
                onClick = onRetry,
                variant = ButtonVariant.PRIMARY,
            )
        }
    }
}
