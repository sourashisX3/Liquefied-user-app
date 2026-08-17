package com.lecomapp.liquefied.features.profile.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.utils.UiText

@Composable
fun ProfileErrorSnackbar(error: UiText?) {
    val context = LocalContext.current
    val snackbarHostState = LocalSnackBarHostState.current
    LaunchedEffect(error) {
        error?.let { snackbarHostState.showSnackbar(it.asString(context)) }
    }
}