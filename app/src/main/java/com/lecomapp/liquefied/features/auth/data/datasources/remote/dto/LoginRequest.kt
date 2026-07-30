package com.lecomapp.liquefied.features.auth.data.datasources.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val emailOrPhone: String,
    val password: String,
)
