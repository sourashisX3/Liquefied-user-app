package com.lecomapp.liquefied.features.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.joelkanyi.jcomposecountrycodepicker.component.CountryCodePicker
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.ui.components.inputs.AppPasswordTextField
import com.lecomapp.liquefied.core.ui.components.inputs.AppTextField
import com.lecomapp.liquefied.core.ui.theme.AppSpacing
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.RegisterAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.RegisterState

@Composable
fun RegisterFormFields(
    state: RegisterState,
    pickerState: CountryCodePicker,
    onAction: (RegisterAction) -> Unit,
    formAlpha: Float = 1f,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.alpha(formAlpha)) {
        AppTextField(
            value = state.firstName,
            onValueChange = { onAction(RegisterAction.OnFirstNameChange(it)) },
            label = stringResource(R.string.register_first_name),
            placeholder = stringResource(R.string.register_first_name_hint),
            leadingIcon = Icons.Filled.Person,
            isError = state.firstNameError != null,
            errorMessage = state.firstNameError?.asString(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        AppTextField(
            value = state.lastName,
            onValueChange = { onAction(RegisterAction.OnLastNameChange(it)) },
            label = stringResource(R.string.register_last_name),
            placeholder = stringResource(R.string.register_last_name_hint),
            leadingIcon = Icons.Filled.Person,
            isError = state.lastNameError != null,
            errorMessage = state.lastNameError?.asString(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        AppTextField(
            value = state.email,
            onValueChange = { onAction(RegisterAction.OnEmailChange(it)) },
            label = stringResource(R.string.register_email),
            placeholder = stringResource(R.string.register_email_hint),
            leadingIcon = Icons.Filled.Email,
            isError = state.emailError != null,
            errorMessage = state.emailError?.asString(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        CountryCodePhoneField(
            pickerState = pickerState,
            text = state.phoneNumber,
            onValueChange = { onAction(RegisterAction.OnPhoneNumberChange(it)) },
            isError = state.phoneNumberError != null,
            errorMessage = state.phoneNumberError?.asString(),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        AppPasswordTextField(
            value = state.password,
            onValueChange = { onAction(RegisterAction.OnPasswordChange(it)) },
            label = stringResource(R.string.register_password),
            placeholder = stringResource(R.string.register_password_hint),
            leadingIcon = Icons.Filled.Lock,
            isError = state.passwordError != null,
            errorMessage = state.passwordError?.asString(),
            showPassword = state.showPassword,
            onTogglePassword = { onAction(RegisterAction.TogglePasswordVisibility) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(AppSpacing.sm))

        AppPasswordTextField(
            value = state.confirmPassword,
            onValueChange = { onAction(RegisterAction.OnConfirmPasswordChange(it)) },
            label = stringResource(R.string.register_confirm_password),
            placeholder = stringResource(R.string.register_confirm_password_hint),
            leadingIcon = Icons.Filled.Lock,
            isError = state.confirmPasswordError != null,
            errorMessage = state.confirmPasswordError?.asString(),
            showPassword = state.showConfirmPassword,
            onTogglePassword = { onAction(RegisterAction.ToggleConfirmPasswordVisibility) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
