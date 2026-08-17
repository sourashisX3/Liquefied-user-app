package com.lecomapp.liquefied.features.home.data.datasources.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_feed")
data class HomeFeedEntity(
    @PrimaryKey val id: String = "default",
    val json: String,
    val cachedAt: Long,
)

@Entity(tableName = "home_wallet")
data class HomeWalletEntity(
    @PrimaryKey val id: String = "default",
    val balance: Double?,
    val cachedAt: Long,
)