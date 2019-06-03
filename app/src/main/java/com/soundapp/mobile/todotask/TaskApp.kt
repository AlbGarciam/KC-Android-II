package com.soundapp.mobile.todotask

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class TaskApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}