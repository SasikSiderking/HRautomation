package com.example.hrautomation.di

import com.example.hrautomation.di.network_modules.ApiModule
import com.example.hrautomation.di.view_model_modules.ViewModelFactoryModule
import com.example.hrautomation.di.view_model_modules.ViewModelModule
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.colleagues.ColleaguesFragment
import com.example.hrautomation.presentation.view.employee.EmployeeFragment
import com.example.hrautomation.presentation.view.loading.LoadingActivity
import com.example.hrautomation.presentation.view.loading.activity_login.LoginActivity
import com.example.hrautomation.presentation.view.loading.code.CodeLoginFragment
import com.example.hrautomation.presentation.view.loading.email.EmailLoginFragment
import com.example.hrautomation.presentation.view.meeting_room.MeetingRoomFragment
import com.example.hrautomation.presentation.view.product.ProductFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [RepositoryModule::class, ContextModule::class, ApiModule::class,
        ViewModelFactoryModule::class, ViewModelModule::class, ColleagueCashManagerModule::class, DispatchersModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ProductFragment)
    fun inject(fragment: MeetingRoomFragment)
    fun inject(fragment: ColleaguesFragment)
    fun inject(fragment: EmployeeFragment)

    fun inject(activity: LoadingActivity)
    fun inject(activity: LoginActivity)
    fun inject(fragment: EmailLoginFragment)
    fun inject(fragment: CodeLoginFragment)
}
