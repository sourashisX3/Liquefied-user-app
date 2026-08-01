package com.lecomapp.liquefied.features.auth.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.buttons.AppButton
import com.lecomapp.liquefied.core.ui.components.feedback.showTypedSnackBar
import com.lecomapp.liquefied.core.ui.components.inputs.AppTextField
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackBarState
import com.lecomapp.liquefied.features.auth.presentation.animation.animateSequence
import com.lecomapp.liquefied.features.auth.presentation.animation.rememberAuthAnimState
import com.lecomapp.liquefied.features.auth.presentation.components.AuthScreenLayout
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.ForgotPasswordViewModel
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.ForgotPasswordAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.ForgotPasswordEvent
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    onNavigateToOtp: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current
    val typedSnackBarState = LocalTypedSnackBarState.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val anim = rememberAuthAnimState()

    LaunchedEffect(Unit) {
        anim.animateSequence()
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ForgotPasswordEvent.NavigateToOtp -> onNavigateToOtp(event.identifier)
                is ForgotPasswordEvent.NavigateToLogin -> onNavigateToLogin()
                is ForgotPasswordEvent.ShowSnackBar -> {
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(anim.headerAlpha.value)
                .offset(y = (24 * anim.headerOffsetY.value).dp),
        ) {
            Text(
                text = stringResource(R.string.forgot_title),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            Text(
                text = stringResource(R.string.forgot_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.xl))

        AppTextField(
            value = state.identifier,
            onValueChange = { viewModel.onAction(ForgotPasswordAction.OnIdentifierChange(it)) },
            label = stringResource(R.string.forgot_email_or_phone),
            placeholder = stringResource(R.string.forgot_email_or_phone_hint),
            leadingIcon = Icons.Filled.Email,
            isError = state.identifierError != null,
            errorMessage = state.identifierError?.asString(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .alpha(anim.formAlpha.value)
                .offset(y = (24 * anim.formOffsetY.value).dp),
        )

        Spacer(modifier = Modifier.height(AppSpacing.xl))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(anim.buttonAlpha.value)
                .offset(y = (24 * anim.buttonOffsetY.value).dp),
        ) {
            AppButton(
                onClick = { viewModel.onAction(ForgotPasswordAction.Submit) },
                enabled = state.isFormValid && !state.isLoading,
                isLoading = state.isLoading,
                text = stringResource(R.string.forgot_send_code),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(AppSpacing.lg))

            Text(
                text = stringResource(R.string.forgot_back_to_login),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.onAction(ForgotPasswordAction.BackToLogin) }
                    .padding(AppSpacing.sm),
            )
        }
    }
}
