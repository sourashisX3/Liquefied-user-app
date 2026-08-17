package com.lecomapp.liquefied.features.auth.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lecomapp.liquefied.core.ui.components.feedback.showTypedSnackBar
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LiquefiedTheme
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackBarState
import com.lecomapp.liquefied.features.auth.presentation.animation.animateSequence
import com.lecomapp.liquefied.features.auth.presentation.animation.rememberAuthAnimState
import com.lecomapp.liquefied.features.auth.presentation.components.AuthScreenLayout
import com.lecomapp.liquefied.features.auth.presentation.components.LoginActions
import com.lecomapp.liquefied.features.auth.presentation.components.LoginFormFields
import com.lecomapp.liquefied.features.auth.presentation.components.LoginHeader
import com.lecomapp.liquefied.features.auth.presentation.components.LoginSocialButtons
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.LoginScreenViewModel
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.AuthenticationEvent
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.LoginAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.AuthenticationState
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    viewModel: LoginScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current
    val typedSnackBarState = LocalTypedSnackBarState.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is AuthenticationEvent.NavigateToHome -> onNavigateToHome()
                is AuthenticationEvent.NavigateToSignUp -> onNavigateToRegister()
                is AuthenticationEvent.NavigateToForgotPassword -> onNavigateToForgotPassword()
                is AuthenticationEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showTypedSnackBar(
                            event = event.event,
                            context = context,
                            typeState = typedSnackBarState,
                        )
                    }
                }
                else -> {}
            }
        }
    }

    LoginScreenContent(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun LoginScreenContent(
    state: AuthenticationState,
    onAction: (LoginAction) -> Unit,
) {
    val anim = rememberAuthAnimState()

    LaunchedEffect(Unit) {
        anim.animateSequence()
    }

    AuthScreenLayout {
        LoginHeader(
            headerAlpha = anim.headerAlpha.value,
            modifier = Modifier.offset(y = (24 * anim.headerOffsetY.value).dp),
        )

        Spacer(modifier = Modifier.height(AppSpacing.lg))

        LoginFormFields(
            formAlpha = anim.formAlpha.value,
            state = state,
            onAction = onAction,
            modifier = Modifier.offset(y = (24 * anim.formOffsetY.value).dp),
        )

        Spacer(modifier = Modifier.height(AppSpacing.lg))

        LoginActions(
            buttonAlpha = anim.buttonAlpha.value,
            state = state,
            onAction = onAction,
            modifier = Modifier.offset(y = (24 * anim.buttonOffsetY.value).dp),
        )

        Spacer(modifier = Modifier.height(AppSpacing.lg))

        LoginSocialButtons(
            buttonAlpha = anim.buttonAlpha.value,
            onAction = onAction,
            modifier = Modifier.offset(y = (24 * anim.buttonOffsetY.value).dp),
        )
    }
}

@Preview(name = "Login - Light", showBackground = true)
@Composable
private fun LoginScreenLightPreview() {
    LiquefiedTheme {
        LoginScreenContent(
            state = AuthenticationState(),
            onAction = {},
        )
    }
}

@Preview(name = "Login - Dark", showBackground = true)
@Composable
private fun LoginScreenDarkPreview() {
    LiquefiedTheme(darkTheme = true) {
        LoginScreenContent(
            state = AuthenticationState(),
            onAction = {},
        )
    }
}
