package com.lecomapp.liquefied.core.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale

data class AppLocale(
    val code: String,
    val displayName: String,
)

object LocaleManager {

    private const val PREFS_NAME = "locale_prefs"
    private const val KEY_SELECTED_LOCALE = "selected_locale"

    val supportedLocales = listOf(
        AppLocale(code = "en", displayName = "English"),
        AppLocale(code = "hi", displayName = "हिन्दी"),
        AppLocale(code = "bn", displayName = "বাংলা"),
    )

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getSelectedLocaleCode(context: Context): String =
        prefs(context).getString(KEY_SELECTED_LOCALE, DEFAULT_LOCALE) ?: DEFAULT_LOCALE

    fun getSelectedLocale(context: Context): Locale =
        Locale(getSelectedLocaleCode(context))

    fun isSelected(context: Context, code: String): Boolean =
        getSelectedLocaleCode(context) == code

    fun setLocale(context: Context, code: String) {
        val resolvedCode = supportedLocales.firstOrNull { it.code == code }?.code ?: DEFAULT_LOCALE
        prefs(context).edit().putString(KEY_SELECTED_LOCALE, resolvedCode).apply()
        applyLocale(context.resources, resolvedCode)
    }

    fun withLocale(base: Context): Context {
        val locale = getSelectedLocale(base)
        Locale.setDefault(locale)
        val config = Configuration(base.resources.configuration)
        setLocaleCompat(config, locale)
        return base.createConfigurationContext(config)
    }

    @Suppress("DEPRECATION")
    private fun applyLocale(resources: Resources, code: String) {
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        setLocaleCompat(config, locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun setLocaleCompat(config: Configuration, locale: Locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            config.setLocales(android.os.LocaleList(locale))
        } else {
            config.setLocale(locale)
        }
    }

    private const val DEFAULT_LOCALE = "en"
}
