package com.wreckingballsoftware.design.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.wreckingballsoftware.design.database.DeSignDatabase
import com.wreckingballsoftware.design.domain.DeSignMap
import com.wreckingballsoftware.design.domain.GoogleAuth
import com.wreckingballsoftware.design.repos.CampaignsRepo
import com.wreckingballsoftware.design.repos.SignMarkersRepo
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.campaigns.CampaignsViewModel
import com.wreckingballsoftware.design.ui.details.CampaignDetailsViewModel
import com.wreckingballsoftware.design.ui.login.AuthViewModel
import com.wreckingballsoftware.design.ui.map.MapViewModel
import com.wreckingballsoftware.design.utils.DataStoreWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATA_STORE_NAME = "com.wreckingballsoftware.design"
private const val DB_NAME = "design_db"

val appModule = module {
    viewModel {
        AuthViewModel(
            googleAuth = get(),
            userRepo = get(),
        )
    }

    viewModel {
        CampaignsViewModel(
            handle = get(),
            campaignsRepo = get(),
            userRepo = get(),
        )
    }

    viewModel {
        CampaignDetailsViewModel(
            campaignsRepo = get(),
        )
    }

    viewModel {
        MapViewModel(
            deSignMap = get(),
            userRepo = get(),
            campaignsRepo = get(),
            signMarkersRepo = get(),
        )
    }

    factory {
        UserRepo(
            dataStore = get(),
        )
    }

    single {
        CampaignsRepo(
            campaignsDao = get()
        )
    }

    single {
        SignMarkersRepo(
            signMarkersDao = get()
        )
    }

    single {
        DeSignMap(
            fusedLocationProviderClient = get(),
        )
    }

    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    single {
        DataStoreWrapper(getDataStore(androidContext()))
    }

    single {
        GoogleAuth(androidContext())
    }

    single {
        val database = get<DeSignDatabase>()
        database.getCampaignsDao()
    }

    single {
        val database = get<DeSignDatabase>()
        database.getSignMarkersDao()
    }

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = DeSignDatabase::class.java,
            name = DB_NAME,
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

private fun getDataStore(context: Context) : DataStore<Preferences> =
    PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
        produceFile = { context.preferencesDataStoreFile(DATA_STORE_NAME) },
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    )

