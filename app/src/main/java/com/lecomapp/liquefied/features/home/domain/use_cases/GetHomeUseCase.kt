package com.lecomapp.liquefied.features.home.domain.use_cases

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.home.domain.models.HomeData
import com.lecomapp.liquefied.features.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(
    private val repository: HomeRepository,
) {
    suspend operator fun invoke(): Result<HomeData> = repository.getHome()
}
