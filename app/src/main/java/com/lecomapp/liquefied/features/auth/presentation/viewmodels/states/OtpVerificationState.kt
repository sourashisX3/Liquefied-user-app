package com.lecomapp.liquefied.features.auth.presentation.viewmodels.states

import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.utils.UiText

data class OtpVerificationState(
    val identifier: String = "",
    val otp: String = "",
    val isDirty: Boolean = false,
    val isLoading: Boolean = false,
    val resendCooldownSeconds: Int = 0,
    val error: UiText? = null,
) {
    val otpError: UiText? get() {
        if (!isDirty) return null
        if (otp.isBlank()) return UiText.StringResourceId(R.string.otp_error_required)
        if (otp.length != OTP_LENGTH) return UiText.StringResourceId(R.string.otp_error_invalid)
        return null
    }

    val isFormValid: Boolean get() = otp.length == OTP_LENGTH

    val isResendEnabled: Boolean get() = resendCooldownSeconds <= 0

    companion object {
        const val OTP_LENGTH = 6
        const val RESEND_COOLDOWN_SECONDS = 30
    }
}
