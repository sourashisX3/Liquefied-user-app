package com.lecomapp.liquefied.features.auth.data.datasources.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

@Singleton
class AuthLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val USER_UUID_KEY = stringPreferencesKey("user_uuid")
    }

    val token: Flow<String?> = context.dataStore.data.map { it[TOKEN_KEY] }

    val refreshToken: Flow<String?> = context.dataStore.data.map { it[REFRESH_TOKEN_KEY] }

    suspend fun getToken(): String? = context.dataStore.data.first()[TOKEN_KEY]

    suspend fun getRefreshToken(): String? = context.dataStore.data.first()[REFRESH_TOKEN_KEY]

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit {
            it[TOKEN_KEY] = accessToken
            it[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun saveUserUuid(uuid: String) {
        context.dataStore.edit { it[USER_UUID_KEY] = uuid }
    }

    suspend fun getUserUuid(): String? = context.dataStore.data.first()[USER_UUID_KEY]

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
