package com.wreckingballsoftware.design.repos

import com.wreckingballsoftware.design.database.INVALID_CAMPAIGN_ID
import com.wreckingballsoftware.design.utils.DataStoreWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepo(private val dataStore: DataStoreWrapper) {
    suspend fun putUserGivenName(firstName: String) = withContext(Dispatchers.IO) {
        dataStore.putUserGivenName(firstName)
    }

    suspend fun getUserGivenName(): String = withContext(Dispatchers.IO) {
        dataStore.getUserGivenName("")
    }

    suspend fun putUserFamilyName(lastName: String) = withContext(Dispatchers.IO) {
        dataStore.putUserFamilyName(lastName)
    }

    suspend fun getUserFamilyName(): String = withContext(Dispatchers.IO) {
        dataStore.getUserFamilyName("")
    }

    suspend fun putUserDisplayName(fullName: String) = withContext(Dispatchers.IO) {
        dataStore.putUserFullName(fullName)
    }

    suspend fun getUserDisplayName(): String = withContext(Dispatchers.IO) {
        dataStore.getUserFullName("")
    }

    suspend fun putUserEmail(email: String) = withContext(Dispatchers.IO) {
        dataStore.putUserEmail(email)
    }

    suspend fun getUserEmail(): String = withContext(Dispatchers.IO) {
        dataStore.getUserEmail("")
    }

    suspend fun putSelectedCampaignId(id: Long) = withContext(Dispatchers.IO) {
        dataStore.putSelectedCampaignIndex(id)
    }

    suspend fun getSelectedCampaignId():Long = withContext(Dispatchers.IO) {
        dataStore.getSelectedCampaignIndex(default = INVALID_CAMPAIGN_ID)
    }
}