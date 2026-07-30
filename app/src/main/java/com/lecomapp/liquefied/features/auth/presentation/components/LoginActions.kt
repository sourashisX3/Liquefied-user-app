package com.lecomapp.liquefied.features.auth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.AppButton
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.LoginAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.AuthenticationState

@Composable
fun LoginActions(
    buttonAlpha: Float,
    state: AuthenticationState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.alpha(buttonAlpha),
    ) {
        AppButton(
            onClick = { onAction(LoginAction.Login) },
            enabled = state.isFormValid && !state.isLoading,
            isLoading = state.isLoading,
            text = stringResource(R.string.login_sign_in),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.lg))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.login_dont_have_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            TextButton(
                onClick = { onAction(LoginAction.OnSignUpClick) },
                contentPadding = PaddingValues(horizontal = AppSpacing.sm),
            ) {
                Text(
                    text = stringResource(R.string.login_sign_up),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
