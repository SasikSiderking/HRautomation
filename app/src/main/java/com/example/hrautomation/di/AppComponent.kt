package com.example.hrautomation.di

import com.example.hrautomation.di.network_modules.ApiModule
import com.example.hrautomation.di.view_model_modules.ViewModelFactoryModule
import com.example.hrautomation.di.view_model_modules.ViewModelModule
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.colleagues.ColleaguesFragment
import com.example.hrautomation.presentation.view.colleagues.employee.EmployeeActivity
import com.example.hrautomation.presentation.view.faq.FaqFragment
import com.example.hrautomation.presentation.view.faq.activity_question.QuestionActivity
import com.example.hrautomation.presentation.view.loading.LoadingActivity
import com.example.hrautomation.presentation.view.loading.activity_login.LoginActivity
import com.example.hrautomation.presentation.view.loading.code.CodeLoginFragment
import com.example.hrautomation.presentation.view.loading.email.EmailLoginFragment
import com.example.hrautomation.presentation.view.product.ProductFragment
import com.example.hrautomation.presentation.view.profile.ProfileActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        RepositoryModule::class,
        ContextModule::class,
        ApiModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        MapperModule::class,
        DispatchersModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: ProductFragment)

    fun inject(fragment: ColleaguesFragment)
    fun inject(activity: EmployeeActivity)

    fun inject(fragment: FaqFragment)
    fun inject(activity: QuestionActivity)

    fun inject(activity: LoadingActivity)
    fun inject(activity: LoginActivity)
    fun inject(fragment: EmailLoginFragment)
    fun inject(fragment: CodeLoginFragment)

    fun inject(activity: ProfileActivity)
}
