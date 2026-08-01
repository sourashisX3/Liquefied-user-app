package com.lecomapp.liquefied.features.auth.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.buttons.AppButton
import com.lecomapp.liquefied.core.ui.components.feedback.showTypedSnackBar
import com.lecomapp.liquefied.core.ui.components.inputs.OtpInput
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackBarState
import com.lecomapp.liquefied.features.auth.presentation.components.AuthScreenLayout
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.OtpVerificationViewModel
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.OtpVerificationAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.OtpVerificationEvent
import kotlinx.coroutines.launch

@Composable
fun OtpVerificationScreen(
    identifier: String,
    onNavigateToResetPassword: (identifier: String, otp: String) -> Unit,
    viewModel: OtpVerificationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current
    val typedSnackBarState = LocalTypedSnackBarState.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.onAction(OtpVerificationAction.StartCooldown)
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is OtpVerificationEvent.NavigateToResetPassword -> {
                    onNavigateToResetPassword(event.identifier, event.otp)
                }
                is OtpVerificationEvent.ShowSnackBar -> {
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

    AuthScreenLayout {
        Text(
            text = stringResource(R.string.otp_title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        Text(
            text = stringResource(R.string.otp_subtitle, identifier),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.xl))

        OtpInput(
            value = state.otp,
            onValueChange = { viewModel.onAction(OtpVerificationAction.OnOtpChange(it)) },
            isError = state.otpError != null,
            errorMessage = state.otpError?.asString(),
            autoFocus = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.xl))

        AppButton(
            onClick = { viewModel.onAction(OtpVerificationAction.Submit) },
            enabled = state.isFormValid && !state.isLoading,
            isLoading = state.isLoading,
            text = stringResource(R.string.otp_verify),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        TextButton(
            onClick = { viewModel.onAction(OtpVerificationAction.Resend) },
            enabled = state.isResendEnabled && !state.isLoading,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = if (state.resendCooldownSeconds > 0) {
                    stringResource(R.string.otp_resend_cooldown, state.resendCooldownSeconds)
                } else {
                    stringResource(R.string.otp_resend)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (state.isResendEnabled) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                },
            )
        }
    }
}
