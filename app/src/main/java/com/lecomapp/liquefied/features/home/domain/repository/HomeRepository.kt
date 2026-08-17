package com.lecomapp.liquefied.features.home.domain.repository

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.home.domain.models.HomeData

interface HomeRepository {
    suspend fun getHome(): Result<HomeData>
    suspend fun refreshHome(): Result<HomeData>
    suspend fun getCachedWalletBalance(): Double?
}