package com.lecomapp.liquefied.features.home.data.repository

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.config.network.models.map
import com.lecomapp.liquefied.core.config.network.models.safeApiCall
import com.lecomapp.liquefied.features.home.data.datasources.local.HomeLocalDataSource
import com.lecomapp.liquefied.features.home.data.datasources.remote.HomeApiService
import com.lecomapp.liquefied.features.home.data.datasources.remote.dto.HomeDto
import com.lecomapp.liquefied.features.home.data.mappers.toDomain
import com.lecomapp.liquefied.features.home.domain.models.HomeData
import com.lecomapp.liquefied.features.home.domain.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApiService,
    private val local: HomeLocalDataSource,
) : HomeRepository {

    override suspend fun getHome(): Result<HomeData> {
        val cached = local.getFeed(FEED_TTL_MILLIS)
        if (cached != null) return Result.Success(cached.toDomain())

        return fetchFromNetwork()
    }

    override suspend fun refreshHome(): Result<HomeData> = fetchFromNetwork()

    private suspend fun fetchFromNetwork(): Result<HomeData> =
        when (val result = safeApiCall { api.getHome() }) {
            is Result.Success -> {
                local.saveFeed(result.data)
                result.data.walletBalance?.let { local.saveWalletBalance(it) }
                Result.Success(result.data.toDomain())
            }
            is Result.Error -> {
                val stale = local.getStaleFeed()
                if (stale != null) Result.Success(stale.toDomain()) else Result.Error(result.error)
            }
            is Result.Loading -> Result.Loading
        }

    override suspend fun getCachedWalletBalance(): Double? = local.getWalletBalance(WALLET_TTL_MILLIS)

    companion object {
        private const val FEED_TTL_MILLIS = 5 * 60 * 1000L
        private const val WALLET_TTL_MILLIS = 2 * 60 * 1000L
    }
}