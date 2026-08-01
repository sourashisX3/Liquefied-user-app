package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

import com.lecomapp.liquefied.core.utils.SnackBarEvent

sealed interface AuthenticationEvent {
    data object LoginSuccess : AuthenticationEvent
    data object NavigateToHome : AuthenticationEvent
    data object NavigateToSignUp : AuthenticationEvent
    data object NavigateToForgotPassword : AuthenticationEvent
    data class ShowSnackBar(val event: SnackBarEvent) : AuthenticationEvent
}
