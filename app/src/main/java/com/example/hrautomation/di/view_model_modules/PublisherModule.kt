package com.example.hrautomation.di.view_model_modules

import com.example.hrautomation.utils.publisher.Publisher
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PublisherModule {
    @Singleton
    @Provides
    fun providePublisher(): Publisher = Publisher()
}