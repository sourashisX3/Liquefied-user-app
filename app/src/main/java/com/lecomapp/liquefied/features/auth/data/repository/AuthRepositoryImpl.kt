package com.lecomapp.liquefied.features.auth.data.repository

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.core.config.network.models.map
import com.lecomapp.liquefied.core.config.network.models.safeApiCall
import com.lecomapp.liquefied.core.config.network.models.safeApiCallUnit
import com.lecomapp.liquefied.features.auth.data.datasources.local.AuthLocalDataSource
import com.lecomapp.liquefied.features.auth.data.datasources.remote.AuthApiService
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.LoginRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.RefreshTokenRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.RegisterRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.ResetPasswordRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.SendOtpRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.SendOtpResponse
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.VerifyOtpRequest
import com.lecomapp.liquefied.features.auth.data.mappers.toDomain
import com.lecomapp.liquefied.features.auth.domain.models.AuthTokens
import com.lecomapp.liquefied.features.auth.domain.models.LoginResult
import com.lecomapp.liquefied.features.auth.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val local: AuthLocalDataSource,
) : AuthenticationRepository {

    override fun isLoggedIn(): Flow<Boolean> = local.token.map { !it.isNullOrEmpty() }

    override suspend fun login(request: LoginRequest): Result<LoginResult> {
        val result = safeApiCall { api.login(request) }
        return when (result) {
            is Result.Success -> {
                val tokens = result.data.toDomain()
                local.saveTokens(tokens.accessToken, tokens.refreshToken)
                Result.Success(LoginResult(message = result.message ?: "Login successful", tokens = tokens))
            }
            is Result.Error -> Result.Error(result.error)
            is Result.Loading -> Result.Loading
        }
    }

    override suspend fun register(request: RegisterRequest): Result<AuthTokens> {
        val result = safeApiCall { api.register(request) }
        if (result is Result.Success) {
            local.saveTokens(result.data.token, result.data.refreshToken)
        }
        return result.map { it.toDomain() }
    }

    override suspend fun refreshToken(request: RefreshTokenRequest): Result<AuthTokens> {
        val result = safeApiCall { api.refreshToken(request) }
        if (result is Result.Success) {
            local.saveTokens(result.data.token, result.data.refreshToken)
        }
        return result.map { it.toDomain() }
    }

    override suspend fun sendOtp(emailOrPhone: String): Result<SendOtpResponse> {
        return safeApiCall { api.sendOtp(SendOtpRequest(emailOrPhone)) }
    }

    override suspend fun verifyOtp(emailOrPhone: String, otp: String): Result<AuthTokens> {
        val result = safeApiCall { api.verifyOtp(VerifyOtpRequest(emailOrPhone, otp)) }
        if (result is Result.Success) {
            local.saveTokens(result.data.token, result.data.refreshToken)
        }
        return result.map { it.toDomain() }
    }

    override suspend fun resetPassword(emailOrPhone: String, otp: String, newPassword: String): Result<Unit> {
        return safeApiCallUnit { api.resetPassword(ResetPasswordRequest(emailOrPhone, otp, newPassword)) }
    }

    override suspend fun logout() {
        local.clear()
    }
}
