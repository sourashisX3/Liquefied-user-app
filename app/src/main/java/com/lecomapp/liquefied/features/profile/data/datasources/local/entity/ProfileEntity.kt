package com.lecomapp.liquefied.features.profile.data.datasources.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: String = "default",
    val json: String,
    val cachedAt: Long,
)