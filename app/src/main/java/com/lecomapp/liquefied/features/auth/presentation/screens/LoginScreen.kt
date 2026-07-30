package com.lecomapp.liquefied.features.auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.lecomapp.liquefied.core.ui.components.AppButton
import com.lecomapp.liquefied.core.ui.components.AppPasswordTextField
import com.lecomapp.liquefied.core.ui.components.AppTextField
import com.lecomapp.liquefied.core.ui.components.ButtonVariant
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LocalSnackbarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackbarState
import com.lecomapp.liquefied.features.auth.presentation.animation.animateSequence
import com.lecomapp.liquefied.features.auth.presentation.animation.rememberLoginAnimState
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.LoginScreenViewModel
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.AuthenticationEvent
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.LoginAction

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    viewModel: LoginScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    val typedSnackbarState = LocalTypedSnackbarState.current
    val context = LocalContext.current

    val anim = rememberLoginAnimState()

    LaunchedEffect(Unit) {
        anim.animateSequence()
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is AuthenticationEvent.NavigateToHome -> onNavigateToHome()
                is AuthenticationEvent.NavigateToSignUp -> onNavigateToRegister()
                is AuthenticationEvent.NavigateToForgotPassword -> onNavigateToForgotPassword()
                is AuthenticationEvent.ShowSnackbar -> {
                    typedSnackbarState.currentType = event.event.type
                    snackbarHostState.showSnackbar(event.event.message.asString(context))
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = AppSpacing.lg)
                    .alpha(anim.titleAlpha.value),
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(AppSpacing.sm))

                Text(
                    text = stringResource(R.string.splash_tagline),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = AppSpacing.lg,
                        end = AppSpacing.lg,
                        top = AppSpacing.xl,
                        bottom = AppSpacing.xl,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.login_welcome_back),
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(anim.formAlpha.value),
                )

                Spacer(modifier = Modifier.height(AppSpacing.sm))

                Text(
                    text = stringResource(R.string.login_sign_in_continue),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(anim.formAlpha.value),
                )

                Spacer(modifier = Modifier.height(AppSpacing.lg))

                Column(
                    modifier = Modifier.alpha(anim.formAlpha.value),
                ) {
                    AppTextField(
                        value = state.username,
                        onValueChange = { viewModel.onAction(LoginAction.OnUsernameChange(it)) },
                        label = stringResource(R.string.login_email_or_phone),
                        placeholder = stringResource(R.string.login_email_or_phone_hint),
                        leadingIcon = Icons.Filled.Email,
                        isError = state.usernameError != null,
                        errorMessage = state.usernameError?.let { stringResource(R.string.login_error_email_required) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(AppSpacing.sm))

                    AppPasswordTextField(
                        value = state.password,
                        onValueChange = { viewModel.onAction(LoginAction.OnPasswordChange(it)) },
                        label = stringResource(R.string.login_password),
                        placeholder = stringResource(R.string.login_password_hint),
                        leadingIcon = Icons.Filled.Lock,
                        isError = state.passwordError != null,
                        errorMessage = state.passwordError?.let { stringResource(R.string.login_error_password_required) },
                        showPassword = state.showPassword,
                        onTogglePassword = { viewModel.onAction(LoginAction.TogglePasswordVisibility) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.lg))

                Column(
                    modifier = Modifier.alpha(anim.buttonAlpha.value),
                ) {
                    AppButton(
                        onClick = { viewModel.onAction(LoginAction.Login) },
                        enabled = state.isFormValid && !state.isLoading,
                        isLoading = state.isLoading,
                        text = stringResource(R.string.login_sign_in),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(AppSpacing.md))

                    AppButton(
                        onClick = { viewModel.onAction(LoginAction.OnSignUpClick) },
                        variant = ButtonVariant.OUTLINE,
                        text = stringResource(R.string.login_sign_up_prompt),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(AppSpacing.sm))

                    Text(
                        text = stringResource(R.string.login_forgot_password),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.lg))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        text = "OR",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = AppSpacing.md),
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(AppSpacing.lg))

                AppButton(
                    onClick = { viewModel.onAction(LoginAction.OnGoogleSignIn) },
                    variant = ButtonVariant.OUTLINE,
                    text = "Continue with Google",
                    leadingIcon = Icons.Filled.AccountCircle,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(AppSpacing.sm))

                AppButton(
                    onClick = { viewModel.onAction(LoginAction.OnFacebookSignIn) },
                    variant = ButtonVariant.OUTLINE,
                    text = "Continue with Facebook",
                    leadingIcon = Icons.Filled.AccountCircle,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
