package com.lecomapp.liquefied.features.auth.data.datasources.remote

import com.lecomapp.liquefied.core.config.network.ApiConstants
import com.lecomapp.liquefied.core.network.ApiResponse
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.AuthResponse
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.LoginRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.RefreshTokenRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST(ApiConstants.Auth.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<AuthResponse>>

    @POST(ApiConstants.Auth.REGISTER)
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<AuthResponse>>

    @POST(ApiConstants.Auth.REFRESH)
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<ApiResponse<AuthResponse>>

    @POST(ApiConstants.Auth.LOGOUT)
    suspend fun logout(): Response<ApiResponse<Unit>>
}
