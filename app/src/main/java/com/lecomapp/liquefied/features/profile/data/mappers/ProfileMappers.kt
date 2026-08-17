package com.lecomapp.liquefied.features.profile.data.mappers

import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.UserResponse
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser

fun UserResponse.toDomain(): ProfileUser = ProfileUser(
    uuid = uuid,
    firstName = firstName,
    lastName = lastName,
    email = email,
    dialCode = dialCode,
    phoneNumber = phoneNumber,
    profilePictureUrl = profilePictureUrl,
    streetAddress = streetAddress,
    city = city,
    state = state,
    country = country,
    zipCode = zipCode,
    createdAt = createdAt,
)