package com.lecomapp.liquefied.features.auth.domain.models

data class LoginResult(
    val message: String,
    val tokens: AuthTokens,
)
