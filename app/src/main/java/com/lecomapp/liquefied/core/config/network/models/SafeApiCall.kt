package com.lecomapp.liquefied.core.config.network.models

import com.lecomapp.liquefied.core.network.ApiErrorResponse
import com.lecomapp.liquefied.core.network.ApiResponse
import com.lecomapp.liquefied.core.utils.UiText
import kotlinx.serialization.json.Json
import retrofit2.Response

suspend fun <T> safeApiCall(
    json: Json = Json { ignoreUnknownKeys = true },
    apiCall: suspend () -> Response<ApiResponse<T>>,
): Result<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.response != null) {
                Result.Success(body.response, body.message)
            } else {
                Result.Error(body?.message?.toUiText() ?: UiText.DynamicString("Unknown error"))
            }
        } else {
            val bodyMessage = try {
                response.errorBody()?.string()
                    ?.let { json.decodeFromString<ApiErrorResponse>(it).message }
            } catch (e: Exception) {
                null
            }
            Result.Error(
                (bodyMessage ?: response.message())
                    .takeIf { !it.isNullOrBlank() }
                    ?.toUiText()
                    ?: UiText.DynamicString("Unknown error")
            )
        }
    } catch (e: Exception) {
        Result.Error(e.toUiText())
    }
}

suspend fun safeApiCallUnit(
    json: Json = Json { ignoreUnknownKeys = true },
    apiCall: suspend () -> Response<ApiResponse<Unit>>,
): Result<Unit> {
    return try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            Result.Success(Unit, response.body()?.message)
        } else {
            val bodyMessage = try {
                response.errorBody()?.string()
                    ?.let { json.decodeFromString<ApiErrorResponse>(it).message }
            } catch (e: Exception) {
                null
            }
            Result.Error(
                (bodyMessage ?: response.message())
                    .takeIf { !it.isNullOrBlank() }
                    ?.toUiText()
                    ?: UiText.DynamicString("Unknown error")
            )
        }
    } catch (e: Exception) {
        Result.Error(e.toUiText())
    }
}
