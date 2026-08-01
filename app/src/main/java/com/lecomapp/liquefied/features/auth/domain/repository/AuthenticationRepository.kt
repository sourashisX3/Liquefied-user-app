package com.lecomapp.liquefied.features.auth.domain.repository

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.LoginRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.RefreshTokenRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.RegisterRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.SendOtpResponse
import com.lecomapp.liquefied.features.auth.domain.models.AuthTokens
import com.lecomapp.liquefied.features.auth.domain.models.LoginResult

interface AuthenticationRepository {
    suspend fun login(request: LoginRequest): Result<LoginResult>
    suspend fun register(request: RegisterRequest): Result<AuthTokens>
    suspend fun refreshToken(request: RefreshTokenRequest): Result<AuthTokens>
    suspend fun sendOtp(emailOrPhone: String): Result<SendOtpResponse>
    suspend fun verifyOtp(emailOrPhone: String, otp: String): Result<AuthTokens>
    suspend fun resetPassword(emailOrPhone: String, otp: String, newPassword: String): Result<Unit>
    suspend fun logout()
}
