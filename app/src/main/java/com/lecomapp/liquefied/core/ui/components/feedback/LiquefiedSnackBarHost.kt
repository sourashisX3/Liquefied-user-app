package com.lecomapp.liquefied.core.ui.components.feedback

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun LiquefiedSnackBarHost(
    hostState: SnackbarHostState,
    typeState: TypedSnackBarState,
    modifier: Modifier = Modifier,
) {
    val currentData = hostState.currentSnackbarData

    LaunchedEffect(currentData) {
        if (currentData != null) {
            delay(typeState.currentType.displayDurationMillis().milliseconds)
            currentData.dismiss()
        }
    }

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
                EnterTransition.None togetherWith
                    (slideOutVertically(animationSpec = tween(250)) { -it } +
                        fadeOut(animationSpec = tween(250)))
            },
            label = "liquefiedSnackBar",
        ) { data ->
            if (data != null) {
                SwipeDismissableSnackBar(data = data, typeState = typeState)
            }
        }
    }
}

@Composable
private fun SwipeDismissableSnackBar(
    data: SnackbarData,
    typeState: TypedSnackBarState,
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
        TypedSnackBar(data = data, typeState = typeState)
    }
}
