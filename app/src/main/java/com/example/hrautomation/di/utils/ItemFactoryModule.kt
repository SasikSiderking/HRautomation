package com.example.hrautomation.di.utils

import com.example.hrautomation.presentation.model.factory.EventEventItemFactoryImpl
import com.example.hrautomation.presentation.model.factory.EventItemFactory
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface ItemFactoryModule {
    @Reusable
    @Binds
    fun provideItemFactory(eventItemFactoryImpl: EventEventItemFactoryImpl): EventItemFactory
}