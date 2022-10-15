package com.example.hrautomation.di

import android.content.Context
import com.example.hrautomation.data.repository.ProductRepository
import com.example.hrautomation.data.repository.UserRepository
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.product.ProductFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [RepositoryModule::class,ContextModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ProductFragment)
}
@Module
class RepositoryModule {

    @Provides
    fun provideProductRepository(): ProductRepository {
        return ProductRepository()
    }

    @Provides
    fun provideUserRepository(context: Context): UserRepository {
        return UserRepository(context = context)
    }
}
@Module
class ContextModule(private val context: Context){
    @Provides
    fun getContext(): Context =context.applicationContext
}
