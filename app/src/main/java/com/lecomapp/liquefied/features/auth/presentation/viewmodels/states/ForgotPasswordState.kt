package com.lecomapp.liquefied.features.auth.presentation.viewmodels.states

import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.utils.UiText

data class ForgotPasswordState(
    val identifier: String = "",
    val isDirty: Boolean = false,
    val isLoading: Boolean = false,
    val error: UiText? = null,
) {
    val identifierError: UiText? get() {
        if (!isDirty) return null
        if (identifier.isBlank()) {
            return UiText.StringResourceId(R.string.forgot_error_identifier_required)
        }
        return null
    }

    val isFormValid: Boolean get() = identifier.isNotBlank()
}
