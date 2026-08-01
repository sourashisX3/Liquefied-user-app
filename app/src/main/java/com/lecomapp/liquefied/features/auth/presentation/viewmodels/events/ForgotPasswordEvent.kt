package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

import com.lecomapp.liquefied.core.utils.SnackBarEvent

sealed interface ForgotPasswordEvent {
    data class NavigateToOtp(val identifier: String) : ForgotPasswordEvent
    data object NavigateToLogin : ForgotPasswordEvent
    data class ShowSnackBar(val event: SnackBarEvent) : ForgotPasswordEvent
}
