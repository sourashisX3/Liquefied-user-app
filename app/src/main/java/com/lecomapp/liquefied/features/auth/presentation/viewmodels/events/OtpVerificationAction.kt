package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

sealed interface OtpVerificationAction {
    data class OnOtpChange(val otp: String) : OtpVerificationAction
    data object Submit : OtpVerificationAction
    data object Resend : OtpVerificationAction
    data object StartCooldown : OtpVerificationAction
    data object DismissError : OtpVerificationAction
}
