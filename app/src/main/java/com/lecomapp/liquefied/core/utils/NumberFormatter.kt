package com.lecomapp.liquefied.core.utils

import java.util.Locale

private const val THOUSAND = 1_000.0
private const val MILLION = 1_000_000.0
private const val BILLION = 1_000_000_000.0

fun Double.formatCompact(): String {
    val value = kotlin.math.abs(this)
    return when {
        value >= BILLION -> formatWithSuffix(this, BILLION, "B")
        value >= MILLION -> formatWithSuffix(this, MILLION, "M")
        value >= THOUSAND -> formatWithSuffix(this, THOUSAND, "K")
        else -> String.format(Locale.US, "%.2f", this)
    }
}

fun Double.formatWalletAmount(currency: String = "₹"): String {
    return "$currency${this.formatCompact()}"
}

private fun formatWithSuffix(value: Double, divisor: Double, suffix: String): String {
    val scaled = value / divisor
    val rounded = kotlin.math.round(scaled * 100.0) / 100.0
    val text = if (rounded == rounded.toLong().toDouble()) {
        rounded.toLong().toString()
    } else {
        String.format(Locale.US, "%.2f", rounded)
    }
    return "$text$suffix"
}