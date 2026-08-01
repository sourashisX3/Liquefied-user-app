package com.lecomapp.liquefied.features.auth.data.datasources.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOtpRequest(
    val emailOrPhone: String,
    val otp: String,
)
