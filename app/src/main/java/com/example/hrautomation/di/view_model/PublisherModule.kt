package com.example.hrautomation.di.view_model

import com.example.hrautomation.utils.publisher.EventFilterPublisher
import com.example.hrautomation.utils.publisher.ProfilePublisher
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PublisherModule {
    @Singleton
    @Provides
    fun provideProfilePublisher(): ProfilePublisher = ProfilePublisher()

    @Singleton
    @Provides
    fun provideEventFilterPublisher(): EventFilterPublisher = EventFilterPublisher()
}