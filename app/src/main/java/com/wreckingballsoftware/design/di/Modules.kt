package com.wreckingballsoftware.design.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.wreckingballsoftware.design.domain.GoogleAuth
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.login.AuthViewModel
import com.wreckingballsoftware.design.utils.DataStoreWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATA_STORE_NAME = "com.wreckingballsoftware.design"

val appModule = module {
    viewModel {
        AuthViewModel(
            googleAuth = get(),
            userRepo = get(),
        )
    }

    factory {
        UserRepo(
            dataStore = get(),
        )
    }

    factory {
        DataStoreWrapper(getDataStore(androidContext()))
    }

    single {
        GoogleAuth(androidContext())
    }
}

private fun getDataStore(context: Context) : DataStore<Preferences> =
    PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
        produceFile = { context.preferencesDataStoreFile(DATA_STORE_NAME) },
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    )

