package com.lecomapp.liquefied.core.ui.components.common

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.feedback.LiquefiedOptionSheet
import com.lecomapp.liquefied.core.ui.components.feedback.SheetOption
import com.lecomapp.liquefied.core.utils.LocaleManager

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun LanguageSelector(modifier: Modifier = Modifier) {
    val activity = LocalContext.current.findActivity()
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }

    IconButton(
        onClick = { showSheet = true },
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Outlined.Translate,
            contentDescription = stringResource(R.string.language_selector),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }

    if (showSheet) {
        val currentCode = LocaleManager.getSelectedLocaleCode(context)
        LiquefiedOptionSheet(
            title = stringResource(R.string.language_selector_title),
            options = LocaleManager.supportedLocales.map { locale ->
                SheetOption(
                    label = locale.displayName,
                    selected = currentCode == locale.code,
                )
            },
            onSelect = { index ->
                showSheet = false
                val code = LocaleManager.supportedLocales[index].code
                activity?.let {
                    LocaleManager.setLocale(it, code)
                    it.recreate()
                }
            },
            onDismissRequest = { showSheet = false },
        )
    }
}