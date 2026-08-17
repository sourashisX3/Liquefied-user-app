package com.lecomapp.liquefied.core.location

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private val Context.locationDataStore: DataStore<Preferences> by preferencesDataStore(name = "location_prefs")

@Singleton
class LocationPrefsDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    companion object {
        private val DETECTED_ADDRESS_KEY = stringPreferencesKey("detected_address")
    }

    suspend fun getDetectedAddress(): String? =
        context.locationDataStore.data.first()[DETECTED_ADDRESS_KEY]

    suspend fun saveDetectedAddress(address: String) {
        context.locationDataStore.edit { it[DETECTED_ADDRESS_KEY] = address }
    }
}