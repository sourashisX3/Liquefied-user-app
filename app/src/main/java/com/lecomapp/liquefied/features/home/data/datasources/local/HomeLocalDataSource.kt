package com.lecomapp.liquefied.features.home.data.datasources.local

import com.lecomapp.liquefied.features.home.data.datasources.local.entity.HomeFeedEntity
import com.lecomapp.liquefied.features.home.data.datasources.local.entity.HomeWalletEntity
import com.lecomapp.liquefied.features.home.data.datasources.remote.dto.HomeDto
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeLocalDataSource @Inject constructor(
    private val dao: HomeDao,
    private val json: Json,
) {

    suspend fun getFeed(maxAgeMillis: Long): HomeDto? {
        val entity = dao.getFeed() ?: return null
        if (System.currentTimeMillis() - entity.cachedAt > maxAgeMillis) return null
        return runCatching { json.decodeFromString<HomeDto>(entity.json) }.getOrNull()
    }

    suspend fun getStaleFeed(): HomeDto? {
        val entity = dao.getFeed() ?: return null
        return runCatching { json.decodeFromString<HomeDto>(entity.json) }.getOrNull()
    }

    suspend fun saveFeed(dto: HomeDto) {
        dao.upsertFeed(
            HomeFeedEntity(
                json = json.encodeToString(dto),
                cachedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun getWalletBalance(maxAgeMillis: Long): Double? {
        val entity = dao.getWallet() ?: return null
        if (System.currentTimeMillis() - entity.cachedAt > maxAgeMillis) return null
        return entity.balance
    }

    suspend fun getStaleWalletBalance(): Double? = dao.getWallet()?.balance

    suspend fun saveWalletBalance(balance: Double?) {
        dao.upsertWallet(
            HomeWalletEntity(
                balance = balance,
                cachedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun clear() {
        dao.clearFeed()
        dao.clearWallet()
    }
}