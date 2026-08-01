package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

import com.lecomapp.liquefied.core.utils.SnackBarEvent

sealed interface ResetPasswordEvent {
    data object NavigateToLogin : ResetPasswordEvent
    data class ShowSnackBar(val event: SnackBarEvent) : ResetPasswordEvent
}
