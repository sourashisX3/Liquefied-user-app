package com.lecomapp.liquefied.features.auth.domain.use_cases

import com.lecomapp.liquefied.features.auth.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}