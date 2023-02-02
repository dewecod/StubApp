package com.clearylabs.stubapp.base

import android.app.Application
import com.clearylabs.stubapp.BuildConfig
import com.clearylabs.stubapp.di.appModule
import com.clearylabs.stubapp.di.networkModule
import com.clearylabs.stubapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(listOf(appModule, networkModule, viewModelModule))
        }
    }
}