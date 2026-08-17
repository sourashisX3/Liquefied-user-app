package com.lecomapp.liquefied.core.di

import com.lecomapp.liquefied.features.auth.data.datasources.local.AuthLocalDataSource
import com.lecomapp.liquefied.features.auth.data.datasources.remote.AuthApiService
import com.lecomapp.liquefied.features.auth.data.repository.AuthRepositoryImpl
import com.lecomapp.liquefied.features.auth.domain.repository.AuthenticationRepository
import com.lecomapp.liquefied.features.home.data.datasources.local.HomeLocalDataSource
import com.lecomapp.liquefied.features.home.data.datasources.remote.HomeApiService
import com.lecomapp.liquefied.features.home.data.repository.HomeRepositoryImpl
import com.lecomapp.liquefied.features.home.domain.repository.HomeRepository
import com.lecomapp.liquefied.features.profile.data.datasources.local.ProfileLocalDataSource
import com.lecomapp.liquefied.features.profile.data.repository.ProfileRepositoryImpl
import com.lecomapp.liquefied.features.profile.domain.repository.ProfileRepository
import com.lecomapp.liquefied.features.auth.data.datasources.remote.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApiService,
        local: AuthLocalDataSource,
    ): AuthenticationRepository = AuthRepositoryImpl(api, local)

    @Provides
    @Singleton
    fun provideHomeRepository(
        api: HomeApiService,
        local: HomeLocalDataSource,
    ): HomeRepository = HomeRepositoryImpl(api, local)

    @Provides
    @Singleton
    fun provideProfileRepository(
        api: UserApiService,
        local: ProfileLocalDataSource,
    ): ProfileRepository = ProfileRepositoryImpl(api, local)
}
