package com.lecomapp.liquefied.core.data.local

import com.lecomapp.liquefied.features.home.data.datasources.local.HomeLocalDataSource
import com.lecomapp.liquefied.features.profile.data.datasources.local.ProfileLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheManager @Inject constructor(
    private val homeLocal: HomeLocalDataSource,
    private val profileLocal: ProfileLocalDataSource,
) {

    suspend fun clearAll() {
        homeLocal.clear()
        profileLocal.clear()
    }
}