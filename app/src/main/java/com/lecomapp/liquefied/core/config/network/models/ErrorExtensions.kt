package com.lecomapp.liquefied.core.config.network.models

import com.lecomapp.liquefied.core.utils.UiText

fun Throwable.toUiText(): UiText = UiText.DynamicString(
    value = message ?: "An unexpected error occurred"
)

fun String.toUiText(): UiText = UiText.DynamicString(value = this)
