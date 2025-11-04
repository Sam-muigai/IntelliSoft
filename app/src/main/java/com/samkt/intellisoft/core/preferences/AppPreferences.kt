package com.samkt.intellisoft.core.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

interface AppPreferences {
    suspend fun saveAccessToken(token: String)
    fun getAccessToken(): Flow<String>

    suspend fun saveFullName(fullName: String)
    fun getFullName(): Flow<String>

    suspend fun saveEmail(email: String)
    fun getEmail(): Flow<String>
}


class AppPreferencesImpl(
    private val context: Context
) : AppPreferences {
    override suspend fun saveAccessToken(token: String) {
        context.dataStore.edit {
            it[USER_TOKEN] = token
        }
    }

    override fun getAccessToken(): Flow<String> {
        return context.dataStore.data.map {
            it[USER_TOKEN] ?: ""
        }
    }

    override suspend fun saveFullName(fullName: String) {
        context.dataStore.edit {
            it[FULL_NAME] = fullName
        }
    }

    override fun getFullName(): Flow<String> {
        return context.dataStore.data.map {
            it[FULL_NAME] ?: ""
        }
    }

    override suspend fun saveEmail(email: String) {
        context.dataStore.edit {
            it[EMAIL] = email
        }
    }

    override fun getEmail(): Flow<String> {
        return context.dataStore.data.map {
            it[EMAIL] ?: ""
        }
    }

    companion object {
        private val USER_TOKEN = stringPreferencesKey("access_token")
        private val EMAIL = stringPreferencesKey("email")
        private val FULL_NAME = stringPreferencesKey("full_name")
    }
}