package com.lecomapp.liquefied.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.location.LocationPrefsDataSource
import com.lecomapp.liquefied.core.location.LocationProvider
import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.home.domain.use_cases.GetHomeUseCase
import com.lecomapp.liquefied.features.home.domain.use_cases.RefreshHomeUseCase
import com.lecomapp.liquefied.features.home.domain.models.HomeData
import com.lecomapp.liquefied.features.home.presentation.viewmodels.states.HomeAction
import com.lecomapp.liquefied.features.home.presentation.viewmodels.states.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val refreshHomeUseCase: RefreshHomeUseCase,
    private val locationProvider: LocationProvider,
    private val locationPrefsDataSource: LocationPrefsDataSource,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        loadHome()
        loadDetectedAddress()
    }

    private fun loadDetectedAddress() {
        viewModelScope.launch {
            locationPrefsDataSource.getDetectedAddress()?.let { address ->
                _state.update { it.copy(userAddress = address) }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadHome, HomeAction.Retry -> loadHome()
            HomeAction.Refresh -> refresh()
            HomeAction.Locate -> locate()
        }
    }

    fun locate() {
        if (_state.value.isLocating) return
        _state.update { it.copy(isLocating = true, locationError = null) }
        viewModelScope.launch {
            val location = locationProvider.fetchCurrentLocation()
            val label = location?.let {
                locationProvider.reverseGeocode(it.latitude, it.longitude)
            }
            if (label != null) {
                locationPrefsDataSource.saveDetectedAddress(label)
                _state.update { it.copy(isLocating = false, userAddress = label) }
            } else {
                _state.update {
                    it.copy(
                        isLocating = false,
                        locationError = UiText.StringResourceId(
                            com.lecomapp.liquefied.R.string.home_location_error,
                        ),
                    )
                }
            }
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
            val startTime = System.currentTimeMillis()
            var outcome: Result<HomeData>? = null
            when (val result = refreshHomeUseCase()) {
                is Result.Success -> outcome = result
                is Result.Error -> outcome = result
                is Result.Loading -> Unit
            }
            val remaining = MIN_REFRESH_MILLIS - (System.currentTimeMillis() - startTime)
            if (remaining > 0) delay(remaining)
            when (outcome) {
                is Result.Success -> {
                    _state.update { it.copy(isRefreshing = false, homeData = outcome.data) }
                }
                is Result.Error -> {
                    _state.update { it.copy(isRefreshing = false, error = outcome.error) }
                }
                is Result.Loading, null -> _state.update { it.copy(isRefreshing = false) }
            }
        }
    }

    companion object {
        private const val MIN_REFRESH_MILLIS = 700L
    }
}