package com.lecomapp.liquefied.core.di

import android.content.Context
import androidx.room.Room
import com.lecomapp.liquefied.core.data.local.LiquefiedDatabase
import com.lecomapp.liquefied.features.home.data.datasources.local.HomeDao
import com.lecomapp.liquefied.features.profile.data.datasources.local.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LiquefiedDatabase =
        Room.databaseBuilder(
            context,
            LiquefiedDatabase::class.java,
            "liquefied.db",
        ).build()

    @Provides
    fun provideHomeDao(database: LiquefiedDatabase): HomeDao = database.homeDao()

    @Provides
    fun provideProfileDao(database: LiquefiedDatabase): ProfileDao = database.profileDao()
}