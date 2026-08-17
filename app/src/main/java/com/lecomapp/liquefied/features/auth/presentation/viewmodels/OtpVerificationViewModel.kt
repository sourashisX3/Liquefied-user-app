package com.lecomapp.liquefied.features.auth.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.utils.SnackBarEvent
import com.lecomapp.liquefied.core.utils.SnackBarType
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.auth.domain.use_cases.SendOtpUseCase
import com.lecomapp.liquefied.features.auth.domain.use_cases.VerifyOtpUseCase
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.OtpVerificationAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.OtpVerificationEvent
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.OtpVerificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
) : ViewModel() {

    private val identifier: String = checkNotNull(savedStateHandle["identifier"])

    private val _state = MutableStateFlow(OtpVerificationState(identifier = identifier))
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        OtpVerificationState(identifier = identifier),
    )

    private val _events = Channel<OtpVerificationEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: OtpVerificationAction) {
        when (action) {
            is OtpVerificationAction.OnOtpChange -> {
                _state.update {
                    it.copy(
                        otp = action.otp.filter { char -> char.isDigit() }
                            .take(OtpVerificationState.OTP_LENGTH),
                        isDirty = true,
                        error = null,
                    )
                }
            }
            is OtpVerificationAction.Submit -> submit()
            is OtpVerificationAction.Resend -> resend()
            is OtpVerificationAction.StartCooldown -> startResendCooldown()
            is OtpVerificationAction.DismissError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun submit() {
        val currentState = _state.value
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = verifyOtpUseCase(currentState.identifier, currentState.otp)) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false, isDirty = false) }
                    _events.send(
                        OtpVerificationEvent.NavigateToResetPassword(
                            identifier = currentState.identifier,
                            otp = currentState.otp,
                        )
                    )
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.error) }
                    _events.send(
                        OtpVerificationEvent.ShowSnackBar(
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

    private fun resend() {
        if (!_state.value.isResendEnabled) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = sendOtpUseCase(identifier)) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false, otp = "", isDirty = false) }
                    startResendCooldown()
                    _events.send(
                        OtpVerificationEvent.ShowSnackBar(
                            SnackBarEvent(
                                message = UiText.StringResourceId(R.string.otp_resend_sent),
                                type = SnackBarType.SUCCESS,
                            )
                        )
                    )
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.error) }
                    _events.send(
                        OtpVerificationEvent.ShowSnackBar(
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

    private fun startResendCooldown() {
        viewModelScope.launch {
            _state.update {
                it.copy(resendCooldownSeconds = OtpVerificationState.RESEND_COOLDOWN_SECONDS)
            }
            for (remaining in OtpVerificationState.RESEND_COOLDOWN_SECONDS - 1 downTo 0) {
                delay(1_000L)
                _state.update { it.copy(resendCooldownSeconds = remaining) }
            }
        }
    }
}
