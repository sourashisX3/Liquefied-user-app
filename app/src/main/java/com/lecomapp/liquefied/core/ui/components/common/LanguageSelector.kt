package com.lecomapp.liquefied.core.ui.components.common

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.util.LocaleManager

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun LanguageSelector(modifier: Modifier = Modifier) {
    val activity = LocalContext.current.findActivity()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    IconButton(
        onClick = { showDialog = true },
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Outlined.Translate,
            contentDescription = stringResource(R.string.language_selector),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }

    if (showDialog) {
        val currentCode = LocaleManager.getSelectedLocaleCode(context)
        val onSelect: (String) -> Unit = { code ->
            showDialog = false
            activity?.let {
                LocaleManager.setLocale(it, code)
                it.recreate()
            }
        }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.language_selector_title),
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    LocaleManager.supportedLocales.forEach { locale ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelect(locale.code) }
                                .padding(vertical = AppSpacing.xs),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = currentCode == locale.code,
                                onClick = { onSelect(locale.code) },
                            )
                            Text(
                                text = locale.displayName,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = AppSpacing.sm),
                            )
                        }
                    }
                }
            },
            confirmButton = {},
        )
    }
}
