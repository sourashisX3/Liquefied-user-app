package com.lecomapp.liquefied.features.profile.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.network.AuthEvents
import com.lecomapp.liquefied.core.ui.theme.ThemeManager
import com.lecomapp.liquefied.core.ui.theme.ThemeMode
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.auth.domain.use_cases.LogoutUseCase
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser
import com.lecomapp.liquefied.features.profile.domain.use_cases.GetProfileUseCase
import com.lecomapp.liquefied.features.profile.domain.use_cases.UploadProfilePictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class ProfileUiState(
    val isLoading: Boolean = true,
    val user: ProfileUser? = null,
    val error: UiText? = null,
    val isLoggingOut: Boolean = false,
    val isUploading: Boolean = false,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getProfileUseCase: GetProfileUseCase,
    private val uploadProfilePictureUseCase: UploadProfilePictureUseCase,
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
            when (val result = getProfileUseCase()) {
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

    fun uploadProfilePicture(uri: Uri) {
        if (_state.value.isUploading) return
        _state.update { it.copy(isUploading = true, error = null) }
        viewModelScope.launch {
            val file = copyUriToFile(uri)
            when {
                file == null -> _state.update {
                    it.copy(
                        isUploading = false,
                        error = UiText.StringResourceId(
                            com.lecomapp.liquefied.R.string.profile_picture_error,
                        ),
                    )
                }
                file.length() > MAX_UPLOAD_BYTES -> {
                    file.delete()
                    _state.update {
                        it.copy(
                            isUploading = false,
                            error = UiText.StringResourceId(
                                com.lecomapp.liquefied.R.string.profile_picture_too_large,
                            ),
                        )
                    }
                }
                else -> when (val result = uploadProfilePictureUseCase(file)) {
                    is Result.Success -> {
                        _state.update { it.copy(isUploading = false, user = result.data) }
                    }
                    is Result.Error -> {
                        _state.update { it.copy(isUploading = false, error = result.error) }
                    }
                    is Result.Loading -> Unit
                }
            }
        }
    }

    private fun copyUriToFile(uri: Uri): File? {
        return try {
            val resolver = context.contentResolver
            val mimeType = resolver.getType(uri) ?: "image/jpeg"
            val extension = when (mimeType) {
                "image/png" -> "png"
                "image/webp" -> "webp"
                else -> "jpg"
            }
            val file = File(context.cacheDir, "profile_picture_${System.currentTimeMillis()}.$extension")
            resolver.openInputStream(uri)?.use { input ->
                file.outputStream().use { output -> input.copyTo(output) }
            }
            if (file.length() > 0L) file else null
        } catch (e: Exception) {
            null
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
            try {
                logoutUseCase()
                authEvents.forceLogout()
            } finally {
                _state.update { it.copy(isLoggingOut = false) }
            }
        }
    }

    private companion object {
        const val MAX_UPLOAD_BYTES = 5L * 1024 * 1024
    }
}