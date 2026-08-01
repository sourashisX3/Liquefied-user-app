package com.lecomapp.liquefied.features.auth.domain.use_cases

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.RegisterRequest
import com.lecomapp.liquefied.features.auth.domain.models.AuthTokens
import com.lecomapp.liquefied.features.auth.domain.repository.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        dialCode: String,
        phoneNumber: String,
        password: String,
    ): Result<AuthTokens> {
        return repository.register(
            RegisterRequest(
                firstName = firstName,
                lastName = lastName,
                email = email,
                dialCode = dialCode,
                phoneNumber = phoneNumber,
                password = password,
            )
        )
    }
}
