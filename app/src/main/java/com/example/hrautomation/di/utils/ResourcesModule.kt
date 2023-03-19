package com.example.hrautomation.di.utils

import android.content.Context
import com.example.hrautomation.presentation.resources.StringResourceProviderImpl
import com.example.hrautomation.utils.resources.StringResourceProvider
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class ResourcesModule {
    @Reusable
    @Provides
    fun provideStringResourceProvider(context: Context): StringResourceProvider =
        StringResourceProviderImpl(context.resources)
}