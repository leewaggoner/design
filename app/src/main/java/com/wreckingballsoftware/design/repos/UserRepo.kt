package com.wreckingballsoftware.design.repos

import com.wreckingballsoftware.design.database.INVALID_CAMPAIGN_ID
import com.wreckingballsoftware.design.database.INVALID_SIGN_MARKER_ID
import com.wreckingballsoftware.design.utils.DataStoreWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This class currently looks like a useless layer, and that may be true. I don't know yet. But it's
 * not used in a time sensitive area, so I'll leave it for now.
 */
data class SelectedSignId(
    val campaignId: Long = INVALID_CAMPAIGN_ID,
    val signId: Long = INVALID_SIGN_MARKER_ID,
)

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
        dataStore.putSelectedCampaignId(id)
    }

    suspend fun getSelectedCampaignId(): Long = withContext(Dispatchers.IO) {
        dataStore.getSelectedCampaignId(default = INVALID_CAMPAIGN_ID)
    }

    suspend fun putSelectedSignId(campaignId: Long, signId: Long) = withContext(Dispatchers.IO) {
        dataStore.putSelectedSignId(SelectedSignId(campaignId, signId))
    }

    suspend fun getSelectedSignId(default: SelectedSignId?): SelectedSignId? = withContext(Dispatchers.IO) {
        dataStore.getSelectedSignId() ?: default
    }
}