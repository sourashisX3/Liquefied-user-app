package com.lecomapp.liquefied.core.ui.components

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.lecomapp.liquefied.core.utils.SnackbarEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HandleSnackbarEffect(
    eventFlow: Flow<SnackbarEvent>,
    snackbarHostState: SnackbarHostState,
    context: Context = LocalContext.current,
) {
    LaunchedEffect(Unit) {
        eventFlow.collectLatest { event ->
            snackbarHostState.showSnackbar(event.message.asString(context))
        }
    }
}
