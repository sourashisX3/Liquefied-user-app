package com.lecomapp.liquefied.features.auth.presentation.viewmodels.states

import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.utils.UiText

data class AuthenticationState(
    val username: String = "",
    val password: String = "",
    val isUsernameDirty: Boolean = false,
    val isPasswordDirty: Boolean = false,
    val isLoading: Boolean = false,
    val showPassword: Boolean = false,
    val error: UiText? = null,
) {
    val usernameError: UiText? get() {
        if (!isUsernameDirty) return null
        if (username.isBlank()) return UiText.StringResourceId(R.string.login_error_email_required)
        return null
    }

    val passwordError: UiText? get() {
        if (!isPasswordDirty) return null
        if (password.isBlank()) return UiText.StringResourceId(R.string.login_error_password_required)
        if (password.length < 6) return UiText.StringResourceId(R.string.login_error_password_min)
        return null
    }

    val isFormValid: Boolean get() = username.isNotBlank() && password.length >= 6
}
