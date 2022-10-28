package com.example.hrautomation.di

import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.data.dispatcher.CoroutineDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface DispatchersModule {
    @Reusable
    @Binds
    fun provideDispatchers(dispatchers: CoroutineDispatchers): CoroutineDispatchersImpl
}