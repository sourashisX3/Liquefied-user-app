package com.lecomapp.liquefied.features.profile.data.datasources.local

import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.UserResponse
import com.lecomapp.liquefied.features.profile.data.datasources.local.entity.ProfileEntity
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileLocalDataSource @Inject constructor(
    private val dao: ProfileDao,
    private val json: Json,
) {

    suspend fun getProfile(maxAgeMillis: Long): UserResponse? {
        val entity = dao.getProfile() ?: return null
        if (System.currentTimeMillis() - entity.cachedAt > maxAgeMillis) return null
        return runCatching { json.decodeFromString<UserResponse>(entity.json) }.getOrNull()
    }

    suspend fun getStaleProfile(): UserResponse? {
        val entity = dao.getProfile() ?: return null
        return runCatching { json.decodeFromString<UserResponse>(entity.json) }.getOrNull()
    }

    suspend fun saveProfile(user: UserResponse) {
        dao.upsertProfile(
            ProfileEntity(
                json = json.encodeToString(user),
                cachedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun clear() {
        dao.clear()
    }
}