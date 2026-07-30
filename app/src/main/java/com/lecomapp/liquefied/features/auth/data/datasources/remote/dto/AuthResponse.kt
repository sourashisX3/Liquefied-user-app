package com.lecomapp.liquefied.features.auth.data.datasources.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val user: UserResponse,
)

@Serializable
data class UserResponse(
    val uuid: String,
    val firstName: String,
    val lastName: String? = null,
    val email: String? = null,
    val dialCode: String? = null,
    val phoneNumber: String? = null,
    val avatar: String? = null,
)

@Serializable
data class RegisterRequest(
    val firstName: String,
    val lastName: String? = null,
    val email: String? = null,
    val dialCode: String? = null,
    val phoneNumber: String? = null,
    val password: String,
)

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String,
)
