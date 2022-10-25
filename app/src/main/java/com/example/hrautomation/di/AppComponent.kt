package com.example.hrautomation.di

import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.colleagues.ColleaguesFragment
import com.example.hrautomation.presentation.view.employee.EmployeeFragment
import com.example.hrautomation.presentation.view.loading.activity_load.LoadingActivity
import com.example.hrautomation.presentation.view.loading.code.CodeLogin
import com.example.hrautomation.presentation.view.loading.email.EmailLogin
import com.example.hrautomation.presentation.view.meeting_room.MeetingRoomFragment
import com.example.hrautomation.presentation.view.product.ProductFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [RepositoryModule::class, ContextModule::class, ApiModule::class, NetworkModule::class,
        ViewModelFactoryModule::class, ViewModelModule::class
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
    fun inject(fragment: EmailLogin)
    fun inject(fragment: CodeLogin)
}
