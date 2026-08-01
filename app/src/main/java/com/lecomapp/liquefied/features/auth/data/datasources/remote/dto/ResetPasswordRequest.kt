package com.lecomapp.liquefied.features.auth.data.datasources.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val emailOrPhone: String,
    val otp: String,
    val newPassword: String,
)
