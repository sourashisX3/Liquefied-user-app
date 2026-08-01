package com.lecomapp.liquefied.features.auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.buttons.AppIconButton
import com.lecomapp.liquefied.core.ui.components.buttons.IconButtonSize
import com.lecomapp.liquefied.core.ui.components.buttons.IconButtonVariant
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.LoginAction

@Composable
fun LoginSocialButtons(
    buttonAlpha: Float,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.alpha(buttonAlpha),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = "OR",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = AppSpacing.md),
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(AppSpacing.lg))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppIconButton(
                onClick = { onAction(LoginAction.OnGoogleSignIn) },
                painter = painterResource(R.drawable.google_ic),
                contentDescription = stringResource(R.string.login_google),
                variant = IconButtonVariant.OUTLINED,
                size = IconButtonSize.LARGE,
            )

            Spacer(modifier = Modifier.width(AppSpacing.lg))

            AppIconButton(
                onClick = { onAction(LoginAction.OnFacebookSignIn) },
                painter = painterResource(R.drawable.facebook_ic),
                contentDescription = stringResource(R.string.login_facebook),
                variant = IconButtonVariant.OUTLINED,
                size = IconButtonSize.LARGE,
            )
        }
    }
}
