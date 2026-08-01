package com.lecomapp.liquefied.core.ui.theme

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import com.lecomapp.liquefied.core.ui.components.feedback.TypedSnackBarState

val LocalSnackBarHostState = compositionLocalOf { SnackbarHostState() }
val LocalTypedSnackBarState = compositionLocalOf { TypedSnackBarState() }
