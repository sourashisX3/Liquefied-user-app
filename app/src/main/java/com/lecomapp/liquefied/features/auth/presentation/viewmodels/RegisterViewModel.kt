package com.lecomapp.liquefied.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.utils.SnackBarEvent
import com.lecomapp.liquefied.core.utils.SnackBarType
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.auth.domain.use_cases.RegisterUseCase
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.RegisterAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.RegisterEvent
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.RegisterState
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
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        RegisterState(),
    )

    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()

    private companion object {
        const val MAX_PHONE_TEXT_LENGTH = 20
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnFirstNameChange -> {
                _state.update { it.copy(firstName = action.firstName, isFirstNameDirty = true) }
            }
            is RegisterAction.OnLastNameChange -> {
                _state.update { it.copy(lastName = action.lastName, isLastNameDirty = true) }
            }
            is RegisterAction.OnEmailChange -> {
                _state.update { it.copy(email = action.email, isEmailDirty = true) }
            }
            is RegisterAction.OnDialCodeChange -> {
                _state.update { it.copy(dialCode = action.dialCode, isDialCodeDirty = true) }
            }
            is RegisterAction.OnPhoneNumberChange -> {
                _state.update {
                    it.copy(
                        phoneNumber = action.phoneNumber.take(MAX_PHONE_TEXT_LENGTH),
                        isPhoneNumberDirty = true,
                    )
                }
            }
            is RegisterAction.OnPasswordChange -> {
                _state.update { it.copy(password = action.password, isPasswordDirty = true) }
            }
            is RegisterAction.OnConfirmPasswordChange -> {
                _state.update {
                    it.copy(confirmPassword = action.confirmPassword, isConfirmPasswordDirty = true)
                }
            }
            is RegisterAction.TogglePasswordVisibility -> {
                _state.update { it.copy(showPassword = !it.showPassword) }
            }
            is RegisterAction.ToggleConfirmPasswordVisibility -> {
                _state.update { it.copy(showConfirmPassword = !it.showConfirmPassword) }
            }
            is RegisterAction.Register -> register(action.dialCode, action.phoneNumber)
            is RegisterAction.DismissError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun register(dialCode: String, phoneNumber: String) {
        val currentState = _state.value
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (
                val result = registerUseCase(
                    firstName = currentState.firstName,
                    lastName = currentState.lastName,
                    email = currentState.email,
                    dialCode = dialCode,
                    phoneNumber = phoneNumber,
                    password = currentState.password,
                )
            ) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _events.send(
                        RegisterEvent.ShowSnackBar(
                            SnackBarEvent(
                                message = UiText.StringResourceId(R.string.register_success),
                                type = SnackBarType.SUCCESS,
                            )
                        )
                    )
                    _events.send(RegisterEvent.NavigateToHome)
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.error) }
                    _events.send(
                        RegisterEvent.ShowSnackBar(
                            SnackBarEvent(message = result.error, type = SnackBarType.ERROR)
                        )
                    )
                }
                is Result.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}
