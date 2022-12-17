package com.example.hrautomation.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContentResolverModule {
    @Provides
    fun getContentResolver(context: Context): ContentResolver = context.contentResolver
}