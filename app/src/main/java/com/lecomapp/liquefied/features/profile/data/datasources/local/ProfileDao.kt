package com.lecomapp.liquefied.features.profile.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lecomapp.liquefied.features.profile.data.datasources.local.entity.ProfileEntity

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile WHERE id = :id LIMIT 1")
    suspend fun getProfile(id: String = "default"): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: ProfileEntity)

    @Query("DELETE FROM profile")
    suspend fun clear()
}