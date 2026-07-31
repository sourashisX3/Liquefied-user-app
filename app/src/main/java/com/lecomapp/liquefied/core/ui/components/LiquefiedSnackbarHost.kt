package com.lecomapp.liquefied.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lecomapp.liquefied.core.ui.theme.AppSpacing

@Composable
fun LiquefiedSnackbarHost(
    hostState: SnackbarHostState,
    typeState: TypedSnackbarState,
    modifier: Modifier = Modifier,
) {
    val currentData = hostState.currentSnackbarData

    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = AppSpacing.md),
        contentAlignment = Alignment.TopCenter,
    ) {
        AnimatedContent(
            targetState = currentData,
            transitionSpec = {
                (slideInVertically(animationSpec = tween(250)) { -it } + fadeIn(animationSpec = tween(250)))
                    .togetherWith(slideOutVertically(animationSpec = tween(250)) { -it } + fadeOut(animationSpec = tween(250)))
            },
            label = "liquefiedSnackbar",
        ) { data ->
            if (data != null) {
                SwipeDismissableSnackbar(data = data, typeState = typeState)
            }
        }
    }
}

@Composable
private fun SwipeDismissableSnackbar(
    data: SnackbarData,
    typeState: TypedSnackbarState,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value != SwipeToDismissBoxValue.Settled) {
                data.dismiss()
                true
            } else {
                false
            }
        },
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {},
        modifier = Modifier.fillMaxWidth(),
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,
    ) {
        TypedSnackbar(data = data, typeState = typeState)
    }
}
