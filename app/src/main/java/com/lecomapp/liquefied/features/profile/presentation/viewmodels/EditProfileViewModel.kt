package com.lecomapp.liquefied.features.profile.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.UpdateProfileRequest
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser
import com.lecomapp.liquefied.features.profile.domain.use_cases.GetCachedProfileUseCase
import com.lecomapp.liquefied.features.profile.domain.use_cases.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getCachedProfileUseCase: GetCachedProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
) : ViewModel() {

    val cachedProfile: StateFlow<ProfileUser?> = flow {
        emit(getCachedProfileUseCase())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _saveError = MutableStateFlow<UiText?>(null)
    val saveError: StateFlow<UiText?> = _saveError.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    fun save(request: UpdateProfileRequest) {
        if (_isSaving.value) return
        _isSaving.value = true
        _saveError.value = null
        viewModelScope.launch {
            when (val result = updateProfileUseCase(request)) {
                is Result.Success -> _saveSuccess.value = true
                is Result.Error -> _saveError.value = result.error
                is Result.Loading -> Unit
            }
            _isSaving.value = false
        }
    }

    fun consumeSuccess() {
        _saveSuccess.value = false
    }
}