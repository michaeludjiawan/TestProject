package com.michaeludjiawan

import android.app.Application
import com.michaeludjiawan.testproject.di.dataModule
import com.michaeludjiawan.testproject.di.featureModule
import com.michaeludjiawan.testproject.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(dataModule, networkModule, featureModule))
        }
    }
}