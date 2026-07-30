package com.lecomapp.liquefied.features.auth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.AppPasswordTextField
import com.lecomapp.liquefied.core.ui.components.AppTextField
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.LoginAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.AuthenticationState

@Composable
fun LoginFormFields(
    formAlpha: Float,
    state: AuthenticationState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.alpha(formAlpha),
    ) {
        AppTextField(
            value = state.username,
            onValueChange = { onAction(LoginAction.OnUsernameChange(it)) },
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
            onValueChange = { onAction(LoginAction.OnPasswordChange(it)) },
            label = stringResource(R.string.login_password),
            placeholder = stringResource(R.string.login_password_hint),
            leadingIcon = Icons.Filled.Lock,
            isError = state.passwordError != null,
            errorMessage = state.passwordError?.let {
                stringResource(
                    when {
                        state.password.isBlank() -> R.string.login_error_password_required
                        state.password.length < 8 -> R.string.login_error_password_min
                        !state.password.any { it.isUpperCase() } -> R.string.login_error_password_uppercase
                        !state.password.any { it.isLowerCase() } -> R.string.login_error_password_lowercase
                        !state.password.any { it.isDigit() } -> R.string.login_error_password_digit
                        else -> R.string.login_error_password_special
                    }
                )
            },
            showPassword = state.showPassword,
            onTogglePassword = { onAction(LoginAction.TogglePasswordVisibility) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        Text(
            text = stringResource(R.string.login_forgot_password),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAction(LoginAction.ForgotPassword) },
        )
    }
}
