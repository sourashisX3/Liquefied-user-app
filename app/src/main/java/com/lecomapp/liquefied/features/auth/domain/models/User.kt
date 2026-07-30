package com.lecomapp.liquefied.features.auth.domain.models

data class User(
    val uuid: String,
    val firstName: String,
    val lastName: String?,
    val email: String?,
    val dialCode: String?,
    val phoneNumber: String?,
    val avatar: String?,
)

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
)
