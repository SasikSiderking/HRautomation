package com.example.hrautomation.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hrautomation.data.api.IIUserApi
import com.example.hrautomation.data.api.IUserApi
import com.example.hrautomation.data.repository.ProductRepository
import com.example.hrautomation.data.repository.UserRepository
import com.example.hrautomation.domain.repository.IProductRepository
import com.example.hrautomation.domain.repository.IUserRepository
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.loading.activity_load.LoadingActivity
import com.example.hrautomation.presentation.view.loading.activity_load.LoadingActivityViewModel
import com.example.hrautomation.presentation.view.loading.code.CodeLogin
import com.example.hrautomation.presentation.view.loading.code.CodeLoginViewModel
import com.example.hrautomation.presentation.view.loading.email.EmailLogin
import com.example.hrautomation.presentation.view.loading.email.EmailLoginViewModel
import com.example.hrautomation.presentation.view.meeting_room.MeetingRoomFragment
import com.example.hrautomation.presentation.view.meeting_room.MeetingRoomViewModel
import com.example.hrautomation.presentation.view.product.ProductFragment
import com.example.hrautomation.presentation.view.product.ProductFragmentViewModel
import com.example.hrautomation.utils.ViewModelFactory
import dagger.*
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Component(modules = [RepositoryModule::class,ContextModule::class,ApiModule::class,NetworkModule::class,
    ViewModelFactoryModule::class,ViewModelModule::class
])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ProductFragment)
    fun inject(fragment: MeetingRoomFragment)

    fun inject(activity: LoadingActivity)
    fun inject(fragment: EmailLogin)
    fun inject(fragment: CodeLogin)
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
class ApiModule {
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
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

@Module
object ViewModelFactoryModule{
    @Singleton
    @Provides
    fun viewModelFactory(providerMap: Map<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory{
        return ViewModelFactory(providerMap)
    }
}

@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val value : KClass<out ViewModel>)

@Module
interface ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(ProductFragmentViewModel::class)
    fun bindProductFragmentViewModel(productFragmentViewModel: ProductFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MeetingRoomViewModel::class)
    fun bindMeetingRoomViewModel(meetingRoomViewModel: MeetingRoomViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadingActivityViewModel::class)
    fun bindLoadingActivityViewModel(loadingActivityViewModel: LoadingActivityViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmailLoginViewModel::class)
    fun bindEmailLoginViewModel(emailLoginViewModel: EmailLoginViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CodeLoginViewModel::class)
    fun bindCodeLoginViewModel(codeLoginViewModel: CodeLoginViewModel):ViewModel
}
