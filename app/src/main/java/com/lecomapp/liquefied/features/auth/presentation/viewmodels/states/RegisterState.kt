package com.lecomapp.liquefied.features.auth.presentation.viewmodels.states

import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.utils.UiText

data class RegisterState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val dialCode: String = "+91",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isFirstNameDirty: Boolean = false,
    val isLastNameDirty: Boolean = false,
    val isEmailDirty: Boolean = false,
    val isDialCodeDirty: Boolean = false,
    val isPhoneNumberDirty: Boolean = false,
    val isPasswordDirty: Boolean = false,
    val isConfirmPasswordDirty: Boolean = false,
    val isLoading: Boolean = false,
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val error: UiText? = null,
) {
    val firstNameError: UiText? get() {
        if (!isFirstNameDirty) return null
        if (firstName.isBlank()) return UiText.StringResourceId(R.string.register_error_first_name_required)
        return null
    }

    val lastNameError: UiText? get() {
        if (!isLastNameDirty) return null
        if (lastName.isBlank()) return UiText.StringResourceId(R.string.register_error_last_name_required)
        return null
    }

    val emailError: UiText? get() {
        if (!isEmailDirty) return null
        if (email.isBlank()) return UiText.StringResourceId(R.string.register_error_email_required)
        if (!EMAIL_REGEX.matches(email)) return UiText.StringResourceId(R.string.register_error_email_invalid)
        return null
    }

    val dialCodeError: UiText? get() {
        if (!isDialCodeDirty) return null
        if (dialCode.isBlank()) return UiText.StringResourceId(R.string.register_error_dial_code_required)
        return null
    }

    val phoneNumberError: UiText? get() {
        if (!isPhoneNumberDirty) return null
        if (phoneNumber.isBlank()) return UiText.StringResourceId(R.string.register_error_phone_required)
        if (phoneDigits(phoneNumber).length !in PHONE_DIGIT_COUNT_RANGE) {
            return UiText.StringResourceId(R.string.register_error_phone_invalid)
        }
        return null
    }

    val passwordError: UiText? get() {
        if (!isPasswordDirty) return null
        return passwordErrorFor(password)
    }

    val confirmPasswordError: UiText? get() {
        if (!isConfirmPasswordDirty) return null
        if (confirmPassword.isBlank()) return UiText.StringResourceId(R.string.register_error_confirm_required)
        if (confirmPassword != password) return UiText.StringResourceId(R.string.register_error_confirm_mismatch)
        return null
    }

    val isFormValid: Boolean get() =
        firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            EMAIL_REGEX.matches(email) &&
            dialCode.isNotBlank() &&
            phoneDigits(phoneNumber).length in PHONE_DIGIT_COUNT_RANGE &&
            isPasswordValid(password) &&
            confirmPassword == password

    companion object {
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
        private val PHONE_DIGIT_COUNT_RANGE = 10..15

        fun phoneDigits(phoneNumber: String): String = phoneNumber.filter { it.isDigit() }

        fun isPasswordValid(password: String): Boolean =
            password.length >= 8 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() } &&
                password.any { !it.isLetterOrDigit() }

        fun passwordErrorFor(password: String): UiText? {
            if (password.isBlank()) return UiText.StringResourceId(R.string.register_error_password_required)
            if (password.length < 8) return UiText.StringResourceId(R.string.register_error_password_min)
            if (!password.any { it.isUpperCase() }) return UiText.StringResourceId(R.string.register_error_password_uppercase)
            if (!password.any { it.isLowerCase() }) return UiText.StringResourceId(R.string.register_error_password_lowercase)
            if (!password.any { it.isDigit() }) return UiText.StringResourceId(R.string.register_error_password_digit)
            if (!password.any { !it.isLetterOrDigit() }) return UiText.StringResourceId(R.string.register_error_password_special)
            return null
        }
    }
}
