package com.lecomapp.liquefied.features.auth.data.mappers

import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.AuthResponse
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.UserResponse
import com.lecomapp.liquefied.features.auth.domain.models.AuthTokens
import com.lecomapp.liquefied.features.auth.domain.models.User

fun AuthResponse.toDomain(): AuthTokens = AuthTokens(
    accessToken = token,
    refreshToken = refreshToken,
    tokenType = tokenType,
    expiresIn = expiresIn,
)

fun UserResponse.toDomain(): User = User(
    id = id,
    uuid = uuid,
    firstName = firstName,
    lastName = lastName,
    email = email,
    dialCode = dialCode,
    phoneNumber = phoneNumber,
    profilePictureUrl = profilePictureUrl,
    roleName = roleName,
    streetAddress = streetAddress,
    city = city,
    state = state,
    country = country,
    zipCode = zipCode,
    isActive = isActive,
    isEmailVerified = isEmailVerified,
    isPhoneVerified = isPhoneVerified,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
