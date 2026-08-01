package com.lecomapp.liquefied.core.utils

enum class SnackBarType { SUCCESS, ERROR, WARNING, INFO }

data class SnackBarEvent(
    val message: UiText,
    val type: SnackBarType = SnackBarType.INFO,
)
