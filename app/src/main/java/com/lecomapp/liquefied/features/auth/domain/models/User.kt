package com.lecomapp.liquefied.features.auth.domain.models

data class User(
    val id: Int,
    val uuid: String,
    val firstName: String,
    val lastName: String?,
    val email: String?,
    val dialCode: String?,
    val phoneNumber: String?,
    val profilePictureUrl: String?,
    val roleName: String,
    val streetAddress: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val zipCode: Long?,
    val isActive: Boolean,
    val isEmailVerified: Boolean,
    val isPhoneVerified: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
)
