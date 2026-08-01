package com.lecomapp.liquefied.features.auth.presentation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import com.lecomapp.liquefied.core.ui.components.feedback.showTypedSnackBar
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.core.ui.theme.LocalSnackBarHostState
import com.lecomapp.liquefied.core.ui.theme.LocalTypedSnackBarState
import com.lecomapp.liquefied.features.auth.presentation.components.AuthScreenLayout
import com.lecomapp.liquefied.features.auth.presentation.components.RegisterActions
import com.lecomapp.liquefied.features.auth.presentation.components.RegisterFormFields
import com.lecomapp.liquefied.features.auth.presentation.components.RegisterHeader
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.RegisterViewModel
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.RegisterAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.RegisterEvent
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

    AuthScreenLayout {
        RegisterHeader()

        Spacer(modifier = Modifier.height(AppSpacing.lg))

        RegisterFormFields(
            state = state,
            pickerState = pickerState,
            onAction = viewModel::onAction,
        )

        Spacer(modifier = Modifier.height(AppSpacing.lg))

        RegisterActions(
            isLoading = state.isLoading,
            isFormValid = state.isFormValid,
            onRegister = {
                viewModel.onAction(
                    RegisterAction.Register(
                        dialCode = pickerState.getCountryPhoneCode(),
                        phoneNumber = pickerState.getPhoneNumberWithoutPrefix(),
                    )
                )
            },
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}
