package com.lecomapp.liquefied.features.auth.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.utils.SnackBarEvent
import com.lecomapp.liquefied.core.utils.SnackBarType
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.auth.domain.use_cases.ResetPasswordUseCase
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.ResetPasswordAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.ResetPasswordEvent
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.ResetPasswordState
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
class ResetPasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : ViewModel() {

    private val identifier: String = checkNotNull(savedStateHandle["identifier"])
    private val otp: String = checkNotNull(savedStateHandle["otp"])

    private val _state = MutableStateFlow(ResetPasswordState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        ResetPasswordState(),
    )

    private val _events = Channel<ResetPasswordEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ResetPasswordAction) {
        when (action) {
            is ResetPasswordAction.OnNewPasswordChange -> {
                _state.update {
                    it.copy(newPassword = action.password, isNewPasswordDirty = true, error = null)
                }
            }
            is ResetPasswordAction.OnConfirmPasswordChange -> {
                _state.update {
                    it.copy(
                        confirmPassword = action.password,
                        isConfirmPasswordDirty = true,
                        error = null,
                    )
                }
            }
            is ResetPasswordAction.ToggleNewPasswordVisibility -> {
                _state.update { it.copy(showNewPassword = !it.showNewPassword) }
            }
            is ResetPasswordAction.ToggleConfirmPasswordVisibility -> {
                _state.update { it.copy(showConfirmPassword = !it.showConfirmPassword) }
            }
            is ResetPasswordAction.Submit -> submit()
            is ResetPasswordAction.DismissError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun submit() {
        val currentState = _state.value
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (
                val result = resetPasswordUseCase(
                    emailOrPhone = identifier,
                    otp = otp,
                    newPassword = currentState.newPassword,
                )
            ) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _events.send(
                        ResetPasswordEvent.ShowSnackBar(
                            SnackBarEvent(
                                message = UiText.StringResourceId(R.string.reset_success),
                                type = SnackBarType.SUCCESS,
                            )
                        )
                    )
                    _events.send(ResetPasswordEvent.NavigateToLogin)
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.error) }
                    _events.send(
                        ResetPasswordEvent.ShowSnackBar(
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
