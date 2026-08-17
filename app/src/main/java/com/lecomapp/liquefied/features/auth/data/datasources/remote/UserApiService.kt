package com.lecomapp.liquefied.features.auth.data.datasources.remote

import com.lecomapp.liquefied.core.config.network.ApiConstants
import com.lecomapp.liquefied.core.network.ApiResponse
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {

    @GET(ApiConstants.Profile.USER)
    suspend fun getMe(): Response<ApiResponse<UserResponse>>
}