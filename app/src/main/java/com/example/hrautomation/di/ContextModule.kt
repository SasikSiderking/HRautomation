package com.example.hrautomation.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {
    @Provides
    fun getContext(): Context = context.applicationContext
}