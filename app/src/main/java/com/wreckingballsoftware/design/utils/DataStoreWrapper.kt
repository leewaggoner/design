package com.wreckingballsoftware.design.utils

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.wreckingballsoftware.design.repos.SelectedSignId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


class DataStoreWrapper(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKey {
        val USER_GIVEN_NAME_KEY = stringPreferencesKey("PlayerFirstName")
        val USER_FAMILY_NAME_KEY = stringPreferencesKey("PlayerLastName")
        val USER_FULL_NAME = stringPreferencesKey("PlayerFullName")
        val USER_EMAIL_KEY = stringPreferencesKey("UserEmail")
        val SELECTED_CAMPAIGN_ID_KEY = longPreferencesKey("SelectedCampaignIndex")
        val SELECTED_SIGN_ID_KEY = stringPreferencesKey("SelectedSignId")
    }

    suspend fun putUserGivenName(name: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_GIVEN_NAME_KEY] = name
        }
    }

    suspend fun getUserGivenName(default: String): String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.USER_GIVEN_NAME_KEY] ?: default
    }

    suspend fun putUserFamilyName(name: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_FAMILY_NAME_KEY] = name
        }
    }

    suspend fun getUserFamilyName(default: String): String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.USER_FAMILY_NAME_KEY] ?: default
    }

    suspend fun putUserFullName(fullName: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_FULL_NAME] = fullName

        }
    }

    suspend fun getUserFullName(default: String): String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.USER_FULL_NAME] ?: default
    }

    suspend fun putUserEmail(name: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.USER_EMAIL_KEY] = name
        }
    }

    suspend fun getUserEmail(default: String): String = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.USER_EMAIL_KEY] ?: default
    }

    suspend fun putSelectedCampaignId(id: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SELECTED_CAMPAIGN_ID_KEY] = id
        }
    }

    suspend fun getSelectedCampaignId(default: Long): Long = withContext(Dispatchers.IO) {
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.SELECTED_CAMPAIGN_ID_KEY] ?: default
    }

    suspend fun putSelectedSignId(selectedSignId: SelectedSignId) {
        val signIn = Gson().toJson(selectedSignId)
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SELECTED_SIGN_ID_KEY] = signIn
        }
    }

    /**
     * returns null if the JSON string is not found
     */
    suspend fun getSelectedSignId(): SelectedSignId? = withContext(Dispatchers.IO) {
        val signId = dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.first()[PreferencesKey.SELECTED_SIGN_ID_KEY] ?: ""
        Gson().fromJson(signId as String, SelectedSignId::class.java)
    }

    suspend fun clearAll() = withContext(Dispatchers.IO) {
        dataStore.edit {
            it.clear()
        }
    }
}