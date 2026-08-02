package com.lecomapp.liquefied.features.home.presentation.viewmodels.states

import com.lecomapp.liquefied.core.utils.UiText
import com.lecomapp.liquefied.features.home.domain.models.HomeData

data class HomeState(
    val isLoading: Boolean = false,
    val homeData: HomeData? = null,
    val error: UiText? = null,
)

sealed interface HomeAction {
    data object LoadHome : HomeAction
    data object Retry : HomeAction
}
