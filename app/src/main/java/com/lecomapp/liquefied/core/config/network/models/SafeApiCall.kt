package com.lecomapp.liquefied.core.config.network.models

import com.lecomapp.liquefied.core.network.ApiResponse
import com.lecomapp.liquefied.core.utils.UiText
import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<ApiResponse<T>>): Result<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.response != null) {
                Result.Success(body.response)
            } else {
                Result.Error(body?.message?.toUiText() ?: UiText.DynamicString("Unknown error"))
            }
        } else {
            Result.Error(response.message().toUiText())
        }
    } catch (e: Exception) {
        Result.Error(e.toUiText())
    }
}
