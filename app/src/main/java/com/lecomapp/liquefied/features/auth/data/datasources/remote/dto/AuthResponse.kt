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
    val id: Int,
    val uuid: String,
    val firstName: String,
    val lastName: String? = null,
    val email: String? = null,
    val dialCode: String? = null,
    val phoneNumber: String? = null,
    val profilePictureUrl: String? = null,
    val roleName: String,
    val streetAddress: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val zipCode: Long? = null,
    val isActive: Boolean,
    val isEmailVerified: Boolean,
    val isPhoneVerified: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val dialCode: String,
    val phoneNumber: String,
    val password: String,
)

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String,
)
