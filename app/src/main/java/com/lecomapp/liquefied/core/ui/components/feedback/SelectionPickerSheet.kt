package com.lecomapp.liquefied.core.ui.components.feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.common.AppIcons
import com.lecomapp.liquefied.core.ui.components.inputs.AppTextField
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

data class PickerOption(
    val label: String,
    val searchKeys: List<String> = emptyList(),
    val flagRes: Int? = null,
    val selected: Boolean,
)

@Composable
fun SelectionPickerSheet(
    title: String,
    options: List<PickerOption>,
    onSelect: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var query by remember { mutableStateOf("") }

    val filtered = remember(query, options) {
        val normalized = query.trim()
        if (normalized.isEmpty()) {
            options.mapIndexed { index, option -> index to option }
        } else {
            options.mapIndexedNotNull { index, option ->
                val haystack = option.label + " " + option.searchKeys.joinToString(" ")
                if (haystack.contains(normalized, ignoreCase = true)) index to option else null
            }
        }
    }

    LiquefiedBottomSheet(
        title = title,
        onDismissRequest = onDismissRequest,
    ) {
        AppTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = stringResource(R.string.picker_search_hint),
            leadingIcon = AppIcons.Action.Search,
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(AppSpacing.md))

        if (filtered.isEmpty()) {
            Text(
                text = stringResource(R.string.picker_no_results),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppSpacing.xl),
            )
        } else {
            LazyColumn(
                modifier = Modifier.heightIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.xs),
            ) {
                itemsIndexed(filtered) { _, (index, option) ->
                    SheetOptionRow(
                        flagRes = option.flagRes,
                        label = option.label,
                        selected = option.selected,
                        onClick = { onSelect(index) },
                    )
                }
            }
        }
    }
}