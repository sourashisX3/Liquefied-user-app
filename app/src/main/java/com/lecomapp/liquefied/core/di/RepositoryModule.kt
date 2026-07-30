package com.lecomapp.liquefied.core.di

import com.lecomapp.liquefied.features.auth.data.datasources.local.AuthLocalDataSource
import com.lecomapp.liquefied.features.auth.data.datasources.remote.AuthApiService
import com.lecomapp.liquefied.features.auth.data.repository.AuthRepositoryImpl
import com.lecomapp.liquefied.features.auth.domain.repository.AuthenticationRepository
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
}
