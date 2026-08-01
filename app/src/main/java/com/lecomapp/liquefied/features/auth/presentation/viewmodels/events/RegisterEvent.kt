package com.lecomapp.liquefied.features.auth.presentation.viewmodels.events

import com.lecomapp.liquefied.core.utils.SnackBarEvent

sealed interface RegisterEvent {
    data object NavigateToHome : RegisterEvent
    data class ShowSnackBar(val event: SnackBarEvent) : RegisterEvent
}
