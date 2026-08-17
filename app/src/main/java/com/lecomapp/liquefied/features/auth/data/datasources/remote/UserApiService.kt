package com.lecomapp.liquefied.features.auth.data.datasources.remote

import com.lecomapp.liquefied.core.config.network.ApiConstants
import com.lecomapp.liquefied.core.network.ApiResponse
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.ChangePasswordRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.UpdateProfileRequest
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface UserApiService {

    @GET(ApiConstants.Profile.USER)
    suspend fun getMe(): Response<ApiResponse<UserResponse>>

    @PUT(ApiConstants.Profile.USER)
    suspend fun updateMe(@Body request: UpdateProfileRequest): Response<ApiResponse<UserResponse>>

    @Multipart
    @POST(ApiConstants.Profile.PROFILE_PICTURE)
    suspend fun uploadProfilePicture(
        @Part file: MultipartBody.Part,
    ): Response<ApiResponse<UserResponse>>

    @PUT(ApiConstants.Profile.PASSWORD)
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ApiResponse<Unit>>

    @POST(ApiConstants.Profile.DEACTIVATE)
    suspend fun deactivate(): Response<ApiResponse<Unit>>
}