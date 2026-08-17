package com.lecomapp.liquefied.core.network

import com.lecomapp.liquefied.features.auth.data.datasources.local.AuthLocalDataSource
import com.lecomapp.liquefied.features.auth.data.datasources.remote.AuthApiService
import com.lecomapp.liquefied.features.auth.data.datasources.remote.dto.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptor @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authApiServiceProvider: Provider<AuthApiService>,
    private val authEvents: AuthEvents,
) : Interceptor {

    private val refreshMutex = Mutex()

    private val authApiService: AuthApiService
        get() = authApiServiceProvider.get()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = runBlocking { authLocalDataSource.getToken() }
        val authedRequest = request.newBuilder().apply {
            if (token != null) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()
        val response = chain.proceed(authedRequest)

        if (response.code != 401 || shouldSkipRefresh(request)) {
            return response
        }

        val refreshed = runBlocking { refreshTokenSafely() }
        if (!refreshed) {
            return response
        }

        response.close()
        val newToken = runBlocking { authLocalDataSource.getToken() }
        val retryRequest = request.newBuilder().apply {
            if (newToken != null) {
                addHeader("Authorization", "Bearer $newToken")
            }
        }.build()
        return chain.proceed(retryRequest)
    }

    private fun shouldSkipRefresh(request: okhttp3.Request): Boolean {
        val path = request.url.encodedPath
        return path.contains("/auth/") || path.contains("/otp/")
    }

    private suspend fun refreshTokenSafely(): Boolean = refreshMutex.withLock {
        val refreshToken = authLocalDataSource.getRefreshToken()
        if (refreshToken.isNullOrEmpty()) {
            forceLogout()
            return false
        }

        return try {
            val response = authApiService.refreshToken(RefreshTokenRequest(refreshToken))
            val body = response.body()
            if (response.isSuccessful && body?.response != null) {
                authLocalDataSource.saveTokens(body.response.token, body.response.refreshToken)
                true
            } else {
                forceLogout()
                false
            }
        } catch (_: Exception) {
            forceLogout()
            false
        }
    }

    private suspend fun forceLogout() {
        authLocalDataSource.clear()
        authEvents.forceLogout()
    }
}