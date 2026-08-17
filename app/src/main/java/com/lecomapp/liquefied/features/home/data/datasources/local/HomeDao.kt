package com.lecomapp.liquefied.features.home.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lecomapp.liquefied.features.home.data.datasources.local.entity.HomeFeedEntity
import com.lecomapp.liquefied.features.home.data.datasources.local.entity.HomeWalletEntity

@Dao
interface HomeDao {

    @Query("SELECT * FROM home_feed WHERE id = :id LIMIT 1")
    suspend fun getFeed(id: String = "default"): HomeFeedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFeed(feed: HomeFeedEntity)

    @Query("DELETE FROM home_feed")
    suspend fun clearFeed()

    @Query("SELECT * FROM home_wallet WHERE id = :id LIMIT 1")
    suspend fun getWallet(id: String = "default"): HomeWalletEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWallet(wallet: HomeWalletEntity)

    @Query("DELETE FROM home_wallet")
    suspend fun clearWallet()
}