package com.lecomapp.liquefied.core.network

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthEvents @Inject constructor() {

    private val _events = MutableSharedFlow<Unit>(extraBufferCapacity = 8)

    val events: SharedFlow<Unit> = _events.asSharedFlow()

    fun forceLogout() {
        _events.tryEmit(Unit)
    }
}
