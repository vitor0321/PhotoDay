package com.example.photoday

import android.app.Application
import com.example.photoday.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PhotoDayApp : Application() {
    override fun onCreate() {
        super.onCreate()
        modulesKoin()
    }

    private fun modulesKoin() {
        startKoin {
            androidLogger(org.koin.core.logger.Level.NONE)
            androidContext(this@PhotoDayApp)

            modules(appModules)
        }
    }
}