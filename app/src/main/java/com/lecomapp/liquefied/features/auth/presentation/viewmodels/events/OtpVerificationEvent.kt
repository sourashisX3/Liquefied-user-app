package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

import com.lecomapp.liquefied.core.utils.SnackBarEvent

sealed interface OtpVerificationEvent {
    data class NavigateToResetPassword(val identifier: String, val otp: String) : OtpVerificationEvent
    data class ShowSnackBar(val event: SnackBarEvent) : OtpVerificationEvent
}
