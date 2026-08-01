package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

sealed interface RegisterAction {
    data class OnFirstNameChange(val firstName: String) : RegisterAction
    data class OnLastNameChange(val lastName: String) : RegisterAction
    data class OnEmailChange(val email: String) : RegisterAction
    data class OnDialCodeChange(val dialCode: String) : RegisterAction
    data class OnPhoneNumberChange(val phoneNumber: String) : RegisterAction
    data class OnPasswordChange(val password: String) : RegisterAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : RegisterAction
    data object TogglePasswordVisibility : RegisterAction
    data object ToggleConfirmPasswordVisibility : RegisterAction
    data class Register(
        val dialCode: String,
        val phoneNumber: String,
    ) : RegisterAction
    data object DismissError : RegisterAction
}
