package com.example.hrautomation.di

import android.content.Context
import com.example.hrautomation.data.repository.ProductRepository
import com.example.hrautomation.data.repository.UserRepository
import com.example.hrautomation.domain.repository.IProductRepository
import com.example.hrautomation.domain.repository.IUserRepository
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.meeting_room.MeetingRoomFragment
import com.example.hrautomation.presentation.view.product.ProductFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [RepositoryModule::class,ContextModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ProductFragment)
    fun inject(fragment: MeetingRoomFragment)
}
@Module
interface RepositoryModule {

    @Binds
    fun provideProductRepository(repo: ProductRepository): IProductRepository

    @Binds
    fun provideUserRepository(userRepository: UserRepository): IUserRepository
}
@Module
class ContextModule(private val context: Context){
    @Provides
    fun getContext(): Context =context.applicationContext
}
