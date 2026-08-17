package com.lecomapp.liquefied.features.auth.domain.use_cases

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.auth.domain.repository.AuthenticationRepository
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
) {
    suspend operator fun invoke(emailOrPhone: String, otp: String): Result<Unit> {
        return repository.verifyOtp(emailOrPhone, otp)
    }
}
