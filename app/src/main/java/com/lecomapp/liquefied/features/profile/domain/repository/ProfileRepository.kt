package com.lecomapp.liquefied.features.profile.domain.repository

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser

interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileUser>
    suspend fun getCachedProfile(): ProfileUser?
}