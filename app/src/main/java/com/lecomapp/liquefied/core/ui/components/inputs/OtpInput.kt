package com.lecomapp.liquefied.core.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.ShapeTokens

@Composable
fun OtpInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    boxCount: Int = 6,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    autoFocus: Boolean = false,
) {
    val digits = value.filter { it.isDigit() }.take(boxCount)
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(autoFocus, enabled) {
        if (autoFocus && enabled) {
            focusRequester.requestFocus()
        }
    }

    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs),
            ) {
                repeat(boxCount) { index ->
                    OtpBox(
                        digit = digits.getOrNull(index)?.toString() ?: "",
                        isActive = isFocused && index == digits.length,
                        isError = isError,
                        enabled = enabled,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            BasicTextField(
                value = digits,
                onValueChange = { newValue ->
                    onValueChange(newValue.filter { char -> char.isDigit() }.take(boxCount))
                },
                modifier = Modifier
                    .matchParentSize()
                    .focusRequester(focusRequester)
                    .alpha(0f),
                enabled = enabled,
                textStyle = LocalTextStyle.current,
                cursorBrush = SolidColor(Color.Transparent),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() },
                ),
                interactionSource = interactionSource,
                singleLine = true,
            )
        }

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = AppSpacing.xxs, start = AppSpacing.md),
            )
        }
    }
}

@Composable
private fun OtpBox(
    digit: String,
    isActive: Boolean,
    isError: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val borderColor = when {
        isError -> MaterialTheme.colorScheme.error
        isActive -> MaterialTheme.colorScheme.primary
        else -> {
            if (enabled) {
                MaterialTheme.colorScheme.outline
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            }
        }
    }

    Box(
        modifier = modifier
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surface, ShapeTokens.textField)
            .border(
                width = if (isActive || isError) 2.dp else 1.dp,
                color = borderColor,
                shape = ShapeTokens.textField,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = digit,
            style = MaterialTheme.typography.headlineMedium,
            color = if (enabled) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
        )
    }
}
