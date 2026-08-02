package com.lecomapp.liquefied.features.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.inputs.SearchBar
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun HomeHeader(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.home_welcome),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(AppSpacing.md))
        var query by rememberSaveable { mutableStateOf("") }
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            placeholder = stringResource(R.string.home_search_hint),
        )
    }
}
