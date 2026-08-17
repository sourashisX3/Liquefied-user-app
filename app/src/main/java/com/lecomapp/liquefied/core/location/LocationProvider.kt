package com.lecomapp.liquefied.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class LocationProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    /**
     * Fast location strategy used by modern ecommerce apps:
     * 1. Last known location - instant, may be stale.
     * 2. Balanced current location (10s timeout) - fresh fix in a few seconds,
     *    battery friendly, accurate enough for city-level display.
     * 3. High accuracy fallback if balanced fails or times out.
     */
    @SuppressLint("MissingPermission")
    suspend fun fetchCurrentLocation(): Location? {
        runCatching {
            fusedLocationClient.lastLocation.await()
        }.getOrNull()?.let { return it }

        val balanced = runCatching {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                null,
            ).awaitWithTimeout(BALANCED_TIMEOUT_MILLIS)
        }.getOrNull()
        if (balanced != null) return balanced

        return runCatching {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null,
            ).awaitWithTimeout(HIGH_ACCURACY_TIMEOUT_MILLIS)
        }.getOrNull()
    }

    /**
     * Converts coordinates into a short, human-readable place label
     * ("Bengaluru, Karnataka"). Falls back to a compact coordinate string.
     */
    fun reverseGeocode(latitude: Double, longitude: Double): String {
        return runCatching {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.firstOrNull()?.let { address ->
                val parts = listOfNotNull(
                    address.locality,
                    address.subAdminArea,
                    address.adminArea,
                ).filter { it.isNotBlank() }
                if (parts.isNotEmpty()) {
                    parts.take(2).joinToString(", ")
                } else {
                    null
                }
            }
        }.getOrNull() ?: String.format(Locale.US, "%.4f, %.4f", latitude, longitude)
    }

    private suspend fun <T> Task<T>.await(): T? =
        suspendCancellableCoroutine { continuation ->
            addOnSuccessListener { result ->
                if (continuation.isActive) continuation.resume(result)
            }
            addOnFailureListener { error ->
                if (continuation.isActive) continuation.resumeWith(Result.failure(error))
            }
            addOnCanceledListener {
                if (continuation.isActive) continuation.resume(null)
            }
        }

    private suspend fun Task<Location>.awaitWithTimeout(
        timeoutMillis: Long,
    ): Location? {
        return withTimeoutOrNull(timeoutMillis) {
            this@awaitWithTimeout.await()
        }
    }

    private companion object {
        const val BALANCED_TIMEOUT_MILLIS = 10_000L
        const val HIGH_ACCURACY_TIMEOUT_MILLIS = 15_000L
    }
}