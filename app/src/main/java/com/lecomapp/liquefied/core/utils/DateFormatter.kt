package com.lecomapp.liquefied.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private val displayFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern(Constants.DISPLAY_DATE_FORMAT)

fun String.toDisplayDate(): String {
    return this.toDisplayDateOrNull() ?: this
}

fun String.toDisplayDateOrNull(): String? {
    return parseIsoDateTime()?.format(displayFormatter)
}

private fun String.parseIsoDateTime(): LocalDateTime? {
    return try {
        OffsetDateTime.parse(this).toLocalDateTime()
    } catch (e: Exception) {
        toLocalDateTime()
    } ?: try {
        LocalDate.parse(this).atStartOfDay()
    } catch (e: Exception) {
        null
    }
}