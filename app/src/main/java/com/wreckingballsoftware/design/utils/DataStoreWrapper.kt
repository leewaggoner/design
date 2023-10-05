package com.wreckingballsoftware.design.utils

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


class DataStoreWrapper(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKey {
        val USER_GIVEN_NAME_KEY = stringPreferencesKey("PlayerFirstName")
        val USER_FAMILY_NAME_KEY = stringPreferencesKey("PlayerLastName")
        val USER_EMAIL_KEY = stringPreferencesKey("UserEmail")
    }

    suspend fun getUserGivenName(default: String) : String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.USER_GIVEN_NAME_KEY] ?: default
    }

    suspend fun putUserGivenName(name: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_GIVEN_NAME_KEY] = name
        }
    }

    suspend fun getUserFamilyName(default: String) : String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.USER_FAMILY_NAME_KEY] ?: default
    }

    suspend fun putUserFamilyName(name: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_FAMILY_NAME_KEY] = name
        }
    }

    suspend fun getUserEmail(default: String) : String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.USER_EMAIL_KEY] ?: default
    }

    suspend fun putUserEmail(name: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_EMAIL_KEY] = name
        }
    }

    suspend fun clearAll() = withContext(Dispatchers.IO) {
        dataStore.edit {
            it.clear()
        }
    }
}