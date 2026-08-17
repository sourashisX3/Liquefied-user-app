package com.lecomapp.liquefied.core.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun DestinationScreen(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    trailing: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        AppBrandHeader(
            showLogo = false,
            title = title,
            subtitle = subtitle,
            trailing = trailing,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = AppSpacing.lg),
        ) {
            content()
        }
    }
}

@Composable
fun DestinationScreenEmpty(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        AppBrandHeader(
            showLogo = false,
            title = title,
            subtitle = subtitle,
        )
        content()
    }
}