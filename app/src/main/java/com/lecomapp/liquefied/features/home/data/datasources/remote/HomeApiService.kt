package com.lecomapp.liquefied.features.home.data.datasources.remote

import com.lecomapp.liquefied.core.config.network.ApiConstants
import com.lecomapp.liquefied.core.network.ApiResponse
import com.lecomapp.liquefied.features.home.data.datasources.remote.dto.HomeDto
import retrofit2.Response
import retrofit2.http.GET

interface HomeApiService {

    @GET(ApiConstants.Home.HOME)
    suspend fun getHome(): Response<ApiResponse<HomeDto>>
}
