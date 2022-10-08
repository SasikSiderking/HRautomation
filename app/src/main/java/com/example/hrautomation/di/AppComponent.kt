package com.example.hrautomation.di

import com.example.hrautomation.presentation.view.activity.MainActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
}
@Module
class RepositoryModule {
    @Provides
    fun provideMessage(): String {
        return "cho-cho-o motherfucker"
    }
}
