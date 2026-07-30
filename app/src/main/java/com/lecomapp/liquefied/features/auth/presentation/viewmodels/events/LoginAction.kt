package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

sealed interface LoginAction {
    data class OnUsernameChange(val username: String) : LoginAction
    data class OnPasswordChange(val password: String) : LoginAction
    data object Login : LoginAction
    data object ForgotPassword : LoginAction
    data object OnSignUpClick : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data object OnGoogleSignIn : LoginAction
    data object OnFacebookSignIn : LoginAction
    data object DismissError : LoginAction
}
