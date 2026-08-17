package com.lecomapp.liquefied.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.home.domain.use_cases.GetHomeUseCase
import com.lecomapp.liquefied.features.home.presentation.viewmodels.states.HomeAction
import com.lecomapp.liquefied.features.home.presentation.viewmodels.states.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        loadHome()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadHome, HomeAction.Retry -> loadHome()
            HomeAction.Refresh -> refresh()
        }
    }

    private fun loadHome() {
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = getHomeUseCase()) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false, homeData = result.data) }
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.error) }
                }
                is Result.Loading -> Unit
            }
        }
    }

    private fun refresh() {
        _state.update { it.copy(isRefreshing = true, error = null) }
        viewModelScope.launch {
            when (val result = getHomeUseCase()) {
                is Result.Success -> {
                    _state.update { it.copy(isRefreshing = false, homeData = result.data) }
                }
                is Result.Error -> {
                    _state.update { it.copy(isRefreshing = false, error = result.error) }
                }
                is Result.Loading -> Unit
            }
        }
    }
}