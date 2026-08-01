package com.lecomapp.liquefied.features.auth.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joelkanyi.jcomposecountrycodepicker.component.CountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import com.lecomapp.liquefied.core.ui.components.feedback.showTypedSnackBar
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LiquefiedTheme
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackBarState
import com.lecomapp.liquefied.features.auth.presentation.animation.animateSequence
import com.lecomapp.liquefied.features.auth.presentation.animation.rememberAuthAnimState
import com.lecomapp.liquefied.features.auth.presentation.components.AuthScreenLayout
import com.lecomapp.liquefied.features.auth.presentation.components.RegisterActions
import com.lecomapp.liquefied.features.auth.presentation.components.RegisterFormFields
import com.lecomapp.liquefied.features.auth.presentation.components.RegisterHeader
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.RegisterViewModel
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.RegisterAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.RegisterEvent
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.RegisterState
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = LocalSnackBarHostState.current
    val typedSnackBarState = LocalTypedSnackBarState.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val pickerState = rememberKomposeCountryCodePickerState(defaultCountryCode = "IN")

    LaunchedEffect(pickerState.countryCode) {
        viewModel.onAction(RegisterAction.OnDialCodeChange(pickerState.getCountryPhoneCode()))
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is RegisterEvent.NavigateToHome -> onNavigateToHome()
                is RegisterEvent.ShowSnackBar -> {
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

    RegisterScreenContent(
        state = state,
        pickerState = pickerState,
        onAction = viewModel::onAction,
        onNavigateToLogin = onNavigateToLogin,
    )
}

@Composable
fun RegisterScreenContent(
    state: RegisterState,
    pickerState: CountryCodePicker,
    onAction: (RegisterAction) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val anim = rememberAuthAnimState()

    LaunchedEffect(Unit) {
        anim.animateSequence()
    }

    AuthScreenLayout {
        RegisterHeader(
            headerAlpha = anim.headerAlpha.value,
            modifier = Modifier.offset(y = (24 * anim.headerOffsetY.value).dp),
        )

        Spacer(modifier = Modifier.height(AppSpacing.lg))

        RegisterFormFields(
            formAlpha = anim.formAlpha.value,
            state = state,
            pickerState = pickerState,
            onAction = onAction,
            modifier = Modifier.offset(y = (24 * anim.formOffsetY.value).dp),
        )

        Spacer(modifier = Modifier.height(AppSpacing.lg))

        RegisterActions(
            buttonAlpha = anim.buttonAlpha.value,
            isLoading = state.isLoading,
            isFormValid = state.isFormValid,
            onRegister = {
                onAction(
                    RegisterAction.Register(
                        dialCode = pickerState.getCountryPhoneCode(),
                        phoneNumber = pickerState.getPhoneNumberWithoutPrefix(),
                    )
                )
            },
            onNavigateToLogin = onNavigateToLogin,
            modifier = Modifier.offset(y = (24 * anim.buttonOffsetY.value).dp),
        )
    }
}

@Preview(name = "Register - Light", showBackground = true)
@Composable
private fun RegisterScreenLightPreview() {
    LiquefiedTheme {
        RegisterScreenContent(
            state = RegisterState(),
            pickerState = rememberKomposeCountryCodePickerState(defaultCountryCode = "IN"),
            onAction = {},
            onNavigateToLogin = {},
        )
    }
}

@Preview(name = "Register - Dark", showBackground = true)
@Composable
private fun RegisterScreenDarkPreview() {
    LiquefiedTheme(darkTheme = true) {
        RegisterScreenContent(
            state = RegisterState(),
            pickerState = rememberKomposeCountryCodePickerState(defaultCountryCode = "IN"),
            onAction = {},
            onNavigateToLogin = {},
        )
    }
}
