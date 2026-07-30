package com.lecomapp.liquefied.core.ui.theme

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import com.lecomapp.liquefied.core.ui.components.TypedSnackbarState

val LocalSnackbarHostState = compositionLocalOf { SnackbarHostState() }
val LocalTypedSnackbarState = compositionLocalOf { TypedSnackbarState() }
