package com.lecomapp.liquefied.features.auth.data.datasources.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SendOtpResponse(
    val otp: String? = null,
)
