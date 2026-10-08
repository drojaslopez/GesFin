package com.gesfin.widget.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SESSION_PREFERENCES_NAME = "session_preferences"
private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(
    name = SESSION_PREFERENCES_NAME
)

data class SessionData(
    val isLoggedIn: Boolean = false,
    val authToken: String = "",
    val userId: Long = -1L,
    val nombreUsuario: String = "",
    val familyGroupId: Long = -1L
)

object SessionPreferencesKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    val USER_ID = longPreferencesKey("user_id")
    val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
    val FAMILY_GROUP_ID = longPreferencesKey("family_group_id")
}

class SessionDataStore(private val context: Context) {

    val sessionData: Flow<SessionData> = context.sessionDataStore.data.map { preferences ->
        SessionData(
            isLoggedIn = preferences[SessionPreferencesKeys.IS_LOGGED_IN] ?: false,
            authToken = preferences[SessionPreferencesKeys.AUTH_TOKEN] ?: "",
            userId = preferences[SessionPreferencesKeys.USER_ID] ?: -1L,
            nombreUsuario = preferences[SessionPreferencesKeys.NOMBRE_USUARIO] ?: "",
            familyGroupId = preferences[SessionPreferencesKeys.FAMILY_GROUP_ID] ?: -1L
        )
    }

    suspend fun saveSession(
        authToken: String,
        userId: Long,
        nombreUsuario: String,
        familyGroupId: Long
    ) {
        context.sessionDataStore.edit { preferences ->
            preferences[SessionPreferencesKeys.IS_LOGGED_IN] = true
            preferences[SessionPreferencesKeys.AUTH_TOKEN] = authToken
            preferences[SessionPreferencesKeys.USER_ID] = userId
            preferences[SessionPreferencesKeys.NOMBRE_USUARIO] = nombreUsuario
            preferences[SessionPreferencesKeys.FAMILY_GROUP_ID] = familyGroupId
        }
    }

    suspend fun clearSession() {
        context.sessionDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
