package com.example.hrautomation.app

import android.app.Application
import com.example.hrautomation.BuildConfig
import com.example.hrautomation.di.AppComponent
import com.example.hrautomation.di.ContextModule
import com.example.hrautomation.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appComponent = DaggerAppComponent.builder().contextModule(ContextModule(this)).build()
    }
}