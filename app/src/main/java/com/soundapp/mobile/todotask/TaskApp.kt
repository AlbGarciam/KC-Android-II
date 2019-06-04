package com.soundapp.mobile.todotask

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.soundapp.mobile.todotask.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        // Initialize database
        startKoin {
            androidLogger()
            androidContext(this@TaskApp)
            modules(appModule) // Here we instantiate our database
        }
    }
}