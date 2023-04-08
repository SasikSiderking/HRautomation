package com.example.hrautomation.di.utils

import com.example.hrautomation.presentation.model.factory.ItemFactory
import com.example.hrautomation.presentation.model.factory.ItemFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface ItemFactoryModule {
    @Reusable
    @Binds
    fun provideItemFactory(itemFactoryImpl: ItemFactoryImpl): ItemFactory
}