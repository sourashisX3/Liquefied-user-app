package com.lecomapp.liquefied.features.profile.domain.repository

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.UpdateProfileRequest
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser
import java.io.File

interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileUser>
    suspend fun getCachedProfile(): ProfileUser?
    suspend fun updateProfile(request: UpdateProfileRequest): Result<ProfileUser>
    suspend fun uploadProfilePicture(file: File): Result<ProfileUser>
}