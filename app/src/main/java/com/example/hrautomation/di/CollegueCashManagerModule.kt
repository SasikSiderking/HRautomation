package com.example.hrautomation.di

import com.example.hrautomation.presentation.view.colleagues.SelectedColleagueCashManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ColleagueCashManagerModule {
    @Provides
    @Singleton
    fun provideSelectedColleagueCashManager(): SelectedColleagueCashManager = SelectedColleagueCashManager()
}