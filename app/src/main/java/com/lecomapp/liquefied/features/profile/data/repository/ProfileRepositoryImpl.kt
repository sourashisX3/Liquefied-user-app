package com.lecomapp.liquefied.features.profile.data.repository

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.config.network.models.map
import com.lecomapp.liquefied.core.config.network.models.safeApiCall
import com.lecomapp.liquefied.features.auth.data.datasources.remote.UserApiService
import com.lecomapp.liquefied.features.profile.data.datasources.local.ProfileLocalDataSource
import com.lecomapp.liquefied.features.profile.data.mappers.toDomain
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser
import com.lecomapp.liquefied.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val api: UserApiService,
    private val local: ProfileLocalDataSource,
) : ProfileRepository {

    override suspend fun getProfile(): Result<ProfileUser> {
        val cached = local.getProfile(PROFILE_TTL_MILLIS)
        if (cached != null) return Result.Success(cached.toDomain())

        return when (val result = safeApiCall { api.getMe() }) {
            is Result.Success -> {
                local.saveProfile(result.data)
                Result.Success(result.data.toDomain())
            }
            is Result.Error -> {
                val stale = local.getStaleProfile()
                if (stale != null) Result.Success(stale.toDomain()) else Result.Error(result.error)
            }
            is Result.Loading -> Result.Loading
        }
    }

    override suspend fun getCachedProfile(): ProfileUser? = local.getStaleProfile()?.toDomain()

    companion object {
        private const val PROFILE_TTL_MILLIS = 10 * 60 * 1000L
    }
}