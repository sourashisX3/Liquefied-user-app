package com.lecomapp.liquefied.core.config.network

import com.lecomapp.liquefied.BuildConfig

object EnvironmentConfig {
    val baseUrl: String get() = BuildConfig.BASE_URL
    val wsUrl: String get() = BuildConfig.WS_URL
}
