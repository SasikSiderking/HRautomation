package com.example.hrautomation.di.view_model

import com.example.hrautomation.utils.publisher.EventFilterEvent
import com.example.hrautomation.utils.publisher.ProfileEvent
import com.example.hrautomation.utils.publisher.Publisher
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PublisherModule {
    @Singleton
    @Provides
    fun provideProfilePublisher(): Publisher<ProfileEvent> = Publisher()

    @Singleton
    @Provides
    fun provideEventFilterPublisher(): Publisher<EventFilterEvent> = Publisher()
}