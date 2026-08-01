package com.lecomapp.liquefied.features.auth.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.buttons.AppButton
import com.lecomapp.liquefied.core.ui.components.feedback.showTypedSnackBar
import com.lecomapp.liquefied.core.ui.components.inputs.AppPasswordTextField
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LiquefiedTheme
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackBarState
import com.lecomapp.liquefied.features.auth.presentation.animation.animateSequence
import com.lecomapp.liquefied.features.auth.presentation.animation.rememberAuthAnimState
import com.lecomapp.liquefied.features.auth.presentation.components.AuthScreenLayout
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.ResetPasswordViewModel
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.ResetPasswordAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.ResetPasswordEvent
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.ResetPasswordState
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: ResetPasswordViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current
    val typedSnackBarState = LocalTypedSnackBarState.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ResetPasswordEvent.NavigateToLogin -> onNavigateToLogin()
                is ResetPasswordEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showTypedSnackBar(
                            event = event.event,
                            context = context,
                            typeState = typedSnackBarState,
                        )
                    }
                }
            }
        }
    }

    ResetPasswordScreenContent(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun ResetPasswordScreenContent(
    state: ResetPasswordState,
    onAction: (ResetPasswordAction) -> Unit,
) {
    val anim = rememberAuthAnimState()

    LaunchedEffect(Unit) {
        anim.animateSequence()
    }

    AuthScreenLayout {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(anim.headerAlpha.value)
                .offset(y = (24 * anim.headerOffsetY.value).dp),
        ) {
            Text(
                text = stringResource(R.string.reset_title),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            Text(
                text = stringResource(R.string.reset_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.xl))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(anim.formAlpha.value)
                .offset(y = (24 * anim.formOffsetY.value).dp),
        ) {
            AppPasswordTextField(
                value = state.newPassword,
                onValueChange = { onAction(ResetPasswordAction.OnNewPasswordChange(it)) },
                label = stringResource(R.string.reset_new_password),
                placeholder = stringResource(R.string.reset_new_password_hint),
                leadingIcon = Icons.Filled.Lock,
                isError = state.newPasswordError != null,
                errorMessage = state.newPasswordError?.asString(),
                showPassword = state.showNewPassword,
                onTogglePassword = {
                    onAction(ResetPasswordAction.ToggleNewPasswordVisibility)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            AppPasswordTextField(
                value = state.confirmPassword,
                onValueChange = {
                    onAction(ResetPasswordAction.OnConfirmPasswordChange(it))
                },
                label = stringResource(R.string.reset_confirm_password),
                placeholder = stringResource(R.string.reset_confirm_password_hint),
                leadingIcon = Icons.Filled.Lock,
                isError = state.confirmPasswordError != null,
                errorMessage = state.confirmPasswordError?.asString(),
                showPassword = state.showConfirmPassword,
                onTogglePassword = {
                    onAction(ResetPasswordAction.ToggleConfirmPasswordVisibility)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.xl))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(anim.buttonAlpha.value)
                .offset(y = (24 * anim.buttonOffsetY.value).dp),
        ) {
            AppButton(
                onClick = { onAction(ResetPasswordAction.Submit) },
                enabled = state.isFormValid && !state.isLoading,
                isLoading = state.isLoading,
                text = stringResource(R.string.reset_update_password),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "Reset Password - Light", showBackground = true)
@Composable
private fun ResetPasswordScreenLightPreview() {
    LiquefiedTheme {
        ResetPasswordScreenContent(
            state = ResetPasswordState(),
            onAction = {},
        )
    }
}

@Preview(name = "Reset Password - Dark", showBackground = true)
@Composable
private fun ResetPasswordScreenDarkPreview() {
    LiquefiedTheme(darkTheme = true) {
        ResetPasswordScreenContent(
            state = ResetPasswordState(),
            onAction = {},
        )
    }
}
