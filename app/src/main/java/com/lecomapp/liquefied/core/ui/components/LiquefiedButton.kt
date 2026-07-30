package com.lecomapp.liquefied.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.theme.AppButton

@Composable
fun LiquefiedPrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(AppButton.height)
            .fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(AppButton.cornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun LiquefiedSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(AppButton.height)
            .fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(AppButton.cornerRadius),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
        )
    }
}
