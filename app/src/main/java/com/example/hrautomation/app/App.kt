package com.example.hrautomation.app

import android.app.Application
import com.example.hrautomation.di.AppComponent
import com.example.hrautomation.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}