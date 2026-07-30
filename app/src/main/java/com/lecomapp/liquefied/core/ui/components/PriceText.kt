package com.lecomapp.liquefied.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LiquefiedColors

@Composable
fun PriceText(
    amount: Double,
    modifier: Modifier = Modifier,
    oldAmount: Double? = null,
    currency: String = "$",
) {
    Row(modifier = modifier) {
        Text(
            text = "$currency${String.format("%.2f", amount)}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        if (oldAmount != null && oldAmount > amount) {
            Text(
                text = "$currency${String.format("%.2f", oldAmount)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textDecoration = TextDecoration.LineThrough,
                modifier = Modifier.padding(start = AppSpacing.xs),
            )
        }
    }
}
