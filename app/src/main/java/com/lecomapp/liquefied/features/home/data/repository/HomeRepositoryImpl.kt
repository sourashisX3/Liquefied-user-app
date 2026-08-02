package com.lecomapp.liquefied.features.home.data.repository

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.config.network.models.map
import com.lecomapp.liquefied.core.config.network.models.safeApiCall
import com.lecomapp.liquefied.features.home.data.datasources.remote.HomeApiService
import com.lecomapp.liquefied.features.home.data.mappers.toDomain
import com.lecomapp.liquefied.features.home.domain.models.HomeData
import com.lecomapp.liquefied.features.home.domain.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApiService,
) : HomeRepository {

    override suspend fun getHome(): Result<HomeData> {
        return safeApiCall { api.getHome() }.map { it.toDomain() }
    }
}
