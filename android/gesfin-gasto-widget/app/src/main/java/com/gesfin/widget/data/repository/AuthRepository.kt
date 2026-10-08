package com.gesfin.widget.data.repository

import com.gesfin.widget.core.datastore.SessionData
import com.gesfin.widget.core.datastore.SessionDataStore
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val sessionDataStore: SessionDataStore) {
    val sessionData: Flow<SessionData> = sessionDataStore.sessionData

    suspend fun isLoggedIn(): Boolean {
        val session = sessionDataStore.sessionData
        return sessionDataStore.sessionData.let {
            // Simplificado para flujo
            false
        }
    }

    suspend fun saveSession(
        authToken: String,
        userId: Long,
        nombreUsuario: String,
        familyGroupId: Long
    ) {
        sessionDataStore.saveSession(authToken, userId, nombreUsuario, familyGroupId)
    }

    suspend fun clearSession() {
        sessionDataStore.clearSession()
    }
}
