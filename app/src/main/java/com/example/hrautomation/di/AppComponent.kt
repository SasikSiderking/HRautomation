package com.example.hrautomation.di

import com.example.hrautomation.data.repository.ProductRepository
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.product.ProductFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ProductFragment)
}
@Module
class RepositoryModule {
    @Provides
    fun provideMessage(): String {
        return "cho-cho-o"
    }

    @Provides
    fun provideProductRepository(): ProductRepository{
        return ProductRepository()
    }
}
