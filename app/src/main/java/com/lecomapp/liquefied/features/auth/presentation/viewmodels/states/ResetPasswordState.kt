package com.lecomapp.liquefied.features.auth.presentation.viewmodels.states

import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.utils.UiText

data class ResetPasswordState(
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isNewPasswordDirty: Boolean = false,
    val isConfirmPasswordDirty: Boolean = false,
    val isLoading: Boolean = false,
    val showNewPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val error: UiText? = null,
) {
    val newPasswordError: UiText? get() {
        if (!isNewPasswordDirty) return null
        return RegisterState.passwordErrorFor(newPassword)
    }

    val confirmPasswordError: UiText? get() {
        if (!isConfirmPasswordDirty) return null
        if (confirmPassword.isBlank()) {
            return UiText.StringResourceId(R.string.reset_error_confirm_required)
        }
        if (confirmPassword != newPassword) {
            return UiText.StringResourceId(R.string.reset_error_confirm_mismatch)
        }
        return null
    }

    val isFormValid: Boolean get() =
        RegisterState.isPasswordValid(newPassword) && confirmPassword == newPassword
}
