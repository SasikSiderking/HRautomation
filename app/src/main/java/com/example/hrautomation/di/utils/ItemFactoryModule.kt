package com.example.hrautomation.di.utils

import com.example.hrautomation.presentation.model.factory.ItemFactory
import com.example.hrautomation.presentation.model.factory.ItemFactoryImpl
import com.example.hrautomation.utils.resources.StringResourceProvider
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class ItemFactoryModule {
    @Reusable
    @Provides
    fun provideItemFactory(stringResourceProvider: StringResourceProvider): ItemFactory =
        ItemFactoryImpl(stringResourceProvider)
}