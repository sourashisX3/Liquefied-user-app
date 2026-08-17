package com.lecomapp.liquefied.features.profile.domain.models

data class ProfileUser(
    val uuid: String,
    val firstName: String,
    val lastName: String?,
    val email: String?,
    val dialCode: String?,
    val phoneNumber: String?,
    val profilePictureUrl: String?,
    val streetAddress: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val zipCode: Long?,
    val createdAt: String,
) {
    val fullName: String
        get() = listOfNotNull(firstName, lastName).joinToString(" ").ifBlank { "—" }

    val formattedAddress: String?
        get() {
            val parts = listOfNotNull(streetAddress, city, state, country)
                .map { it.trim() }
                .filter { it.isNotBlank() }
            return if (parts.isEmpty()) null else parts.joinToString(", ")
        }

    val phone: String?
        get() = listOfNotNull(dialCode, phoneNumber).joinToString(" ").ifBlank { null }
}