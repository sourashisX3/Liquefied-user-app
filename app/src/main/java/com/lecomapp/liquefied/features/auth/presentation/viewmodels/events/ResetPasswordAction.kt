package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

sealed interface ResetPasswordAction {
    data class OnNewPasswordChange(val password: String) : ResetPasswordAction
    data class OnConfirmPasswordChange(val password: String) : ResetPasswordAction
    data object ToggleNewPasswordVisibility : ResetPasswordAction
    data object ToggleConfirmPasswordVisibility : ResetPasswordAction
    data object Submit : ResetPasswordAction
    data object DismissError : ResetPasswordAction
}
