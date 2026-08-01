package com.lecomapp.liquefied.features.auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lecomapp.liquefied.core.ui.components.common.AnimatedDiamonds
import com.lecomapp.liquefied.core.ui.components.common.AppLogoSection
import com.lecomapp.liquefied.core.ui.components.feedback.showTypedSnackBar
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackBarState
import com.lecomapp.liquefied.features.auth.presentation.animation.animateSequence
import com.lecomapp.liquefied.features.auth.presentation.animation.rememberLoginAnimState
import com.lecomapp.liquefied.features.auth.presentation.components.LoginActions
import com.lecomapp.liquefied.features.auth.presentation.components.LoginFormFields
import com.lecomapp.liquefied.features.auth.presentation.components.LoginHeader
import com.lecomapp.liquefied.features.auth.presentation.components.LoginSocialButtons
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.LoginScreenViewModel
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.AuthenticationEvent
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    viewModel: LoginScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackBarHostState.current
    val typedSnackBarState = LocalTypedSnackBarState.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
                is AuthenticationEvent.ShowSnackBar -> {
                    scope.launch {
                        snackbarHostState.showTypedSnackBar(
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            AnimatedDiamonds(modifier = Modifier.matchParentSize())
            AppLogoSection(
                appNameAlpha = 1f,
                appNameScale = 1f,
                taglineAlpha = 1f,
                taglineOffsetY = 0f,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .offset(y = (80 * anim.cardOffsetY.value).dp)
                .alpha(anim.cardAlpha.value)
                .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
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
                LoginHeader(
                    headerAlpha = anim.headerAlpha.value,
                    modifier = Modifier.offset(y = (24 * anim.headerOffsetY.value).dp),
                )

                Spacer(modifier = Modifier.height(AppSpacing.lg))

                LoginFormFields(
                    formAlpha = anim.formAlpha.value,
                    state = state,
                    onAction = { viewModel.onAction(it) },
                    modifier = Modifier.offset(y = (24 * anim.formOffsetY.value).dp),
                )

                Spacer(modifier = Modifier.height(AppSpacing.lg))

                LoginActions(
                    buttonAlpha = anim.buttonAlpha.value,
                    state = state,
                    onAction = { viewModel.onAction(it) },
                    modifier = Modifier.offset(y = (24 * anim.buttonOffsetY.value).dp),
                )

                Spacer(modifier = Modifier.height(AppSpacing.lg))

                LoginSocialButtons(
                    buttonAlpha = anim.buttonAlpha.value,
                    onAction = { viewModel.onAction(it) },
                    modifier = Modifier.offset(y = (24 * anim.buttonOffsetY.value).dp),
                )
            }
        }
    }
}
