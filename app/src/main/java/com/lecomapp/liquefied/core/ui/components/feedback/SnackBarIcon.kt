package com.lecomapp.liquefied.core.ui.components.feedback

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.utils.SnackBarType

@Composable
fun SnackBarIcon(
    type: SnackBarType,
    tint: Color,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
) {
    val icon: Painter = when (type) {
        SnackBarType.SUCCESS -> painterResource(R.drawable.success_correct)
        SnackBarType.ERROR -> painterResource(R.drawable.exclamation)
        SnackBarType.WARNING -> painterResource(R.drawable.triangle_warning)
        SnackBarType.INFO -> painterResource(R.drawable.info)
    }

    Icon(
        painter = icon,
        contentDescription = null,
        tint = tint,
        modifier = modifier.size(iconSize),
    )
}
