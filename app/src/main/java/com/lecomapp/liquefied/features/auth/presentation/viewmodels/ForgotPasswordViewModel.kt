package com.lecomapp.liquefied.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.R
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.utils.SnackBarEvent
import com.lecomapp.liquefied.core.utils.SnackBarType
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.auth.domain.use_cases.SendOtpUseCase
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.ForgotPasswordAction
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.events.ForgotPasswordEvent
import com.lecomapp.liquefied.features.auth.presentation.viewmodels.states.ForgotPasswordState
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
class ForgotPasswordViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        ForgotPasswordState(),
    )

    private val _events = Channel<ForgotPasswordEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ForgotPasswordAction) {
        when (action) {
            is ForgotPasswordAction.OnIdentifierChange -> {
                _state.update {
                    it.copy(identifier = action.identifier, isDirty = true, error = null)
                }
            }
            is ForgotPasswordAction.Submit -> submit()
            is ForgotPasswordAction.BackToLogin -> {
                _events.trySend(ForgotPasswordEvent.NavigateToLogin)
            }
            is ForgotPasswordAction.DismissError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun submit() {
        val identifier = _state.value.identifier.trim()
        if (identifier.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = sendOtpUseCase(identifier)) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _events.send(
                        ForgotPasswordEvent.ShowSnackBar(
                            SnackBarEvent(
                                message = UiText.StringResourceId(R.string.forgot_otp_sent),
                                type = SnackBarType.SUCCESS,
                            )
                        )
                    )
                    _events.send(ForgotPasswordEvent.NavigateToOtp(identifier))
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.error) }
                    _events.send(
                        ForgotPasswordEvent.ShowSnackBar(
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
