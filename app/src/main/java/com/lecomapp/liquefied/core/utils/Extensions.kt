package com.lecomapp.liquefied.core.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatDisplay(): String {
    return this.format(DateTimeFormatter.ofPattern(Constants.DISPLAY_DATE_FORMAT))
}

fun String.toLocalDateTime(): LocalDateTime? {
    return try {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
    } catch (e: Exception) {
        null
    }
}

fun Double.formatPrice(currency: String = Constants.CURRENCY_SYMBOL): String {
    return "$currency${String.format("%.2f", this)}"
}
