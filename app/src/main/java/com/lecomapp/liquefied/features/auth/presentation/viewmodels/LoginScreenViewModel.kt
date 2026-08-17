package com.lecomapp.liquefied.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.utils.SnackBarEvent
import com.lecomapp.liquefied.core.utils.SnackBarType
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.auth.domain.use_cases.LoginUseCase
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.AuthenticationEvent
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.LoginAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.AuthenticationState
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.LoginReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthenticationState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        AuthenticationState(),
    )

    private val _events = Channel<AuthenticationEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnUsernameChange -> {
                _state.update {
                    it.copy(username = action.username, isUsernameDirty = true)
                }
            }
            is LoginAction.OnPasswordChange -> {
                _state.update {
                    it.copy(password = action.password, isPasswordDirty = true)
                }
            }
            is LoginAction.Login -> login()
            is LoginAction.ForgotPassword -> {
                viewModelScope.launch {
                    _events.send(AuthenticationEvent.NavigateToForgotPassword)
                }
            }
            is LoginAction.OnSignUpClick -> {
                viewModelScope.launch {
                    _events.send(AuthenticationEvent.NavigateToSignUp)
                }
            }
            is LoginAction.TogglePasswordVisibility -> {
                _state.update { it.copy(showPassword = !it.showPassword) }
            }
            is LoginAction.OnGoogleSignIn -> {
                viewModelScope.launch {
                    _events.send(AuthenticationEvent.ShowSnackBar(SnackBarEvent(message = UiText.DynamicString("Google Sign-In coming soon"), type = SnackBarType.INFO)))
                }
            }
            is LoginAction.OnFacebookSignIn -> {
                viewModelScope.launch {
                    _events.send(AuthenticationEvent.ShowSnackBar(SnackBarEvent(message = UiText.DynamicString("Facebook Sign-In coming soon"), type = SnackBarType.INFO)))
                }
            }
            is LoginAction.DismissError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun login() {
        val currentState = _state.value
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _state.update { LoginReducer.loading(it) }

            when (val result = loginUseCase(currentState.username, currentState.password)) {
                is Result.Success -> {
                    _state.update { LoginReducer.success(it) }
                    _events.send(AuthenticationEvent.LoginSuccess)
                    _events.send(
                        AuthenticationEvent.ShowSnackBar(
                            SnackBarEvent(message = UiText.DynamicString(result.data.message), type = SnackBarType.SUCCESS)
                        )
                    )
                    _events.send(AuthenticationEvent.NavigateToHome)
                }
                is Result.Error -> {
                    _state.update { LoginReducer.error(it, result.error) }
                    _events.send(AuthenticationEvent.ShowSnackBar(SnackBarEvent(message = result.error, type = SnackBarType.ERROR)))
                }
                is Result.Loading -> {
                    _state.update { LoginReducer.loading(it) }
                }
            }
        }
    }
}
