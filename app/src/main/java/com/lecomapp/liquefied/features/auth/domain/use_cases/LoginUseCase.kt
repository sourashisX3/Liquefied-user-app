package com.lecomapp.liquefied.features.auth.domain.use_cases

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.LoginRequest
import com.lecomapp.liquefied.features.auth.domain.models.AuthTokens
import com.lecomapp.liquefied.features.auth.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
) {
    suspend operator fun invoke(emailOrPhone: String, password: String): Result<AuthTokens> {
        return repository.login(LoginRequest(emailOrPhone, password))
    }
}
