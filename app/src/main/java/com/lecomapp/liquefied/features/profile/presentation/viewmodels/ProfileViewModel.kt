package com.lecomapp.liquefied.features.profile.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.network.AuthEvents
import com.lecomapp.liquefied.core.ui.theme.ThemeManager
import com.lecomapp.liquefied.core.ui.theme.ThemeMode
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.auth.domain.use_cases.LogoutUseCase
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser
import com.lecomapp.liquefied.features.profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val isLoading: Boolean = true,
    val user: ProfileUser? = null,
    val error: UiText? = null,
    val isLoggingOut: Boolean = false,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val profileRepository: ProfileRepository,
    private val logoutUseCase: LogoutUseCase,
    private val authEvents: AuthEvents,
) : ViewModel() {

    private val themeManager = ThemeManager(context)

    private val _state = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    val themeMode: StateFlow<ThemeMode> = themeManager.themeMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ThemeMode.SYSTEM,
    )

    init {
        loadProfile()
    }

    fun loadProfile() {
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = profileRepository.getProfile()) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false, user = result.data) }
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.error) }
                }
                is Result.Loading -> Unit
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            themeManager.setThemeMode(mode)
        }
    }

    fun logout() {
        if (_state.value.isLoggingOut) return
        _state.update { it.copy(isLoggingOut = true) }
        viewModelScope.launch {
            logoutUseCase()
            authEvents.forceLogout()
            _state.update { it.copy(isLoggingOut = false) }
        }
    }
}