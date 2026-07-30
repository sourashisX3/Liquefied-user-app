package com.lecomapp.liquefied.features.auth.presentation.viewmodels.states

import com.lecomapp.liquefied.core.utils.UiText

object LoginReducer {
    fun loading(state: AuthenticationState): AuthenticationState =
        state.copy(isLoading = true, error = null)

    fun success(state: AuthenticationState): AuthenticationState =
        state.copy(isLoading = false)

    fun error(state: AuthenticationState, error: UiText): AuthenticationState =
        state.copy(isLoading = false, error = error)
}
