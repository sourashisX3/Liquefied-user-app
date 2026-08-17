package com.lecomapp.liquefied.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lecomapp.liquefied.features.home.data.datasources.local.HomeDao
import com.lecomapp.liquefied.features.home.data.datasources.local.entity.HomeFeedEntity
import com.lecomapp.liquefied.features.home.data.datasources.local.entity.HomeWalletEntity
import com.lecomapp.liquefied.features.profile.data.datasources.local.ProfileDao
import com.lecomapp.liquefied.features.profile.data.datasources.local.entity.ProfileEntity

@Database(
    entities = [
        HomeFeedEntity::class,
        HomeWalletEntity::class,
        ProfileEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class LiquefiedDatabase : RoomDatabase() {
    abstract fun homeDao(): HomeDao
    abstract fun profileDao(): ProfileDao
}