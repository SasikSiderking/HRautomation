package com.example.hrautomation.di

import com.example.hrautomation.presentation.view.colleagues.SelectedColleagueCacheManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ColleagueCashManagerModule {
    @Provides
    @Singleton
    fun provideSelectedColleagueCacheManager(): SelectedColleagueCacheManager =
        SelectedColleagueCacheManager()
}