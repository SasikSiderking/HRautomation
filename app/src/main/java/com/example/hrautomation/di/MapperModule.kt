package com.example.hrautomation.di

import com.example.hrautomation.data.model.ProductResponseToProductMapper
import com.example.hrautomation.presentation.model.ProductToListedProductItemMapper
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class MapperModule {
    @Reusable
    @Provides
    fun provideProductResponseToProductMapper(): ProductResponseToProductMapper = ProductResponseToProductMapper()

    @Reusable
    @Provides
    fun provideProductToListedProductItemMapper(): ProductToListedProductItemMapper = ProductToListedProductItemMapper()
}