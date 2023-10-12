package com.wreckingballsoftware.design.repos

import com.wreckingballsoftware.design.utils.DataStoreWrapper

class UserRepo(private val dataStore: DataStoreWrapper) {
    suspend fun putUserGivenName(firstName: String) {
        dataStore.putUserGivenName(firstName)
    }

    suspend fun getUserGivenName(): String {
        return dataStore.getUserGivenName("")
    }

    suspend fun putUserFamilyName(lastName: String) {
        dataStore.putUserFamilyName(lastName)
    }

    suspend fun getUserFamilyName(): String {
        return dataStore.getUserFamilyName("")
    }

    suspend fun putUserDisplayName(fullName: String) {
        dataStore.putUserFullName(fullName)
    }

    suspend fun getUserDisplayName(): String {
        return dataStore.getUserFullName("")
    }

    suspend fun putUserEmail(email: String) {
        dataStore.putUserEmail(email)
    }

    suspend fun getUserEmail(): String {
        return dataStore.getUserEmail("")
    }
}