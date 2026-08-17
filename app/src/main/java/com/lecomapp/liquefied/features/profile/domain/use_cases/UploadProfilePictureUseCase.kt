package com.lecomapp.liquefied.features.profile.domain.use_cases

import com.lecomapp.liquefied.core.config.network.models.Result
import com.lecomapp.liquefied.features.profile.domain.models.ProfileUser
import com.lecomapp.liquefied.features.profile.domain.repository.ProfileRepository
import java.io.File
import javax.inject.Inject

class UploadProfilePictureUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(file: File): Result<ProfileUser> =
        repository.uploadProfilePicture(file)
}