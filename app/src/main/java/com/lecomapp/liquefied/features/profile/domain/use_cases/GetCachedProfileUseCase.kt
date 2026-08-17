package com.lecomapp.liquefied.features.profile.domain.use_cases

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser
import com.lecomapp.liquefied.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetCachedProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(): ProfileUser? = repository.getCachedProfile()
}