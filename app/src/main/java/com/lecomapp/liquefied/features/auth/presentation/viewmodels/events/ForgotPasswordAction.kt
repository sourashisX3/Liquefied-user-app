package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

sealed interface ForgotPasswordAction {
    data class OnIdentifierChange(val identifier: String) : ForgotPasswordAction
    data object Submit : ForgotPasswordAction
    data object BackToLogin : ForgotPasswordAction
    data object DismissError : ForgotPasswordAction
}
