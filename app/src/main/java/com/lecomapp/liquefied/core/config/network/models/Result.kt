package com.lecomapp.liquefied.core.config.network.models

import com.lecomapp.liquefied.core.utils.UiText

sealed interface Result<out T> {
    data class Success<T>(val data: T, val message: String? = null) : Result<T>
    data class Error(val error: UiText) : Result<Nothing>
    data object Loading : Result<Nothing>
}

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data), message)
    is Result.Error -> this
    is Result.Loading -> this
}
