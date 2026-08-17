package com.lecomapp.liquefied.core.ui.components.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.common.AppIcons

object CartFloatingButtonDefaults {
    val size: Dp = 56.dp
    val iconSize: Dp = 26.dp
}

@Composable
fun CartFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier.size(CartFloatingButtonDefaults.size),
    ) {
        Icon(
            imageVector = AppIcons.Action.Cart.second,
            contentDescription = stringResource(R.string.bottom_nav_cart),
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(CartFloatingButtonDefaults.iconSize),
        )
    }
}