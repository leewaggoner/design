package com.wreckingballsoftware.design

import android.app.Application
import com.wreckingballsoftware.design.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DeSignApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DeSignApplication)
            modules(appModule)
        }
    }
}