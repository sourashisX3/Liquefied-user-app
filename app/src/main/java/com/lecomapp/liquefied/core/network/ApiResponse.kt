package com.lecomapp.liquefied.core.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val statusCode: Int = 200,
    val message: String = "Success",
    val response: T? = null,
    val pagination: Pagination? = null,
)

@Serializable
data class Pagination(
    val page: Int = 0,
    val size: Int = 20,
    val totalElements: Long = 0,
    val totalPages: Int = 0,
)

@Serializable
data class ApiErrorResponse(
    val statusCode: Int = 0,
    val message: String? = null,
)

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val code: Int = -1) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}
