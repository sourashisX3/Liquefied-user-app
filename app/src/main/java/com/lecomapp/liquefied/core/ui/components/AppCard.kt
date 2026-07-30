package com.lecomapp.liquefied.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.theme.AppCornerRadius
import com.lecomapp.liquefied.core.ui.theme.AppElevation
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.ShapeTokens

enum class CardVariant {
    ELEVATED, FILLED, OUTLINED
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.ELEVATED,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    when (variant) {
        CardVariant.ELEVATED -> {
            Card(
                modifier = modifier.then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
                shape = ShapeTokens.card,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = AppElevation.xs,
                    pressedElevation = AppElevation.sm,
                ),
            ) { content() }
        }
        CardVariant.FILLED -> {
            Box(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant, ShapeTokens.card)
                    .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                    .padding(AppSpacing.md),
            ) { content() }
        }
        CardVariant.OUTLINED -> {
            Box(
                modifier = modifier
                    .border(1.dp, MaterialTheme.colorScheme.outline, ShapeTokens.card)
                    .background(MaterialTheme.colorScheme.surface, ShapeTokens.card)
                    .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                    .padding(AppSpacing.md),
            ) { content() }
        }
    }
}
