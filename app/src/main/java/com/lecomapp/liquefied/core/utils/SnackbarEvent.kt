package com.lecomapp.liquefied.core.utils

enum class SnackbarType { SUCCESS, ERROR, WARNING, INFO }

data class SnackbarEvent(
    val message: UiText,
    val type: SnackbarType = SnackbarType.INFO,
)
