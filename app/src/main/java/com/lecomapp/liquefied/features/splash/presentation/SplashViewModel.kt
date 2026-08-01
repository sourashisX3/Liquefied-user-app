package com.lecomapp.liquefied.features.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.features.auth.domain.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    repository: AuthenticationRepository,
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> = repository.isLoggedIn()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            initialValue = false,
        )
}
