package com.example.hrautomation.di

import android.content.Context
import com.example.hrautomation.data.api.IIUserApi
import com.example.hrautomation.data.api.IUserApi
import com.example.hrautomation.data.repository.ProductRepository
import com.example.hrautomation.data.repository.UserRepository
import com.example.hrautomation.domain.repository.IProductRepository
import com.example.hrautomation.domain.repository.IUserRepository
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.login_dialog.LoginDialog
import com.example.hrautomation.presentation.view.meeting_room.MeetingRoomFragment
import com.example.hrautomation.presentation.view.product.ProductFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Component(modules = [RepositoryModule::class,ContextModule::class,ApiModule::class,NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ProductFragment)
    fun inject(fragment: MeetingRoomFragment)
    fun inject(dialog: LoginDialog)
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

@Module
class ApiModule(){
    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): IUserApi {
       return retrofit.create(IUserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi2(): IIUserApi{
        return IIUserApi()
    }
}

@Module
class NetworkModule(){

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
