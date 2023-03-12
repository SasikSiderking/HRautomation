package com.example.hrautomation.di.view_model

import androidx.lifecycle.ViewModel
import com.example.hrautomation.presentation.view.activity.MainViewModel
import com.example.hrautomation.presentation.view.colleagues.ColleaguesViewModel
import com.example.hrautomation.presentation.view.colleagues.employee.EmployeeViewModel
import com.example.hrautomation.presentation.view.faq.FaqViewModel
import com.example.hrautomation.presentation.view.faq.question.QuestionViewModel
import com.example.hrautomation.presentation.view.loading.LoadingActivityViewModel
import com.example.hrautomation.presentation.view.loading.code.CodeLoginViewModel
import com.example.hrautomation.presentation.view.loading.email.EmailLoginViewModel
import com.example.hrautomation.presentation.view.loading.login.LoginActivityViewModel
import com.example.hrautomation.presentation.view.product.ProductViewModel
import com.example.hrautomation.presentation.view.profile.ProfileViewModel
import com.example.hrautomation.presentation.view.restaurants.RestaurantsViewModel
import com.example.hrautomation.presentation.view.restaurants.restaurant.RestaurantBottomSheetViewModel
import com.example.hrautomation.presentation.view.restaurants.restaurant_details.RestaurantDetailsViewModel
import com.example.hrautomation.presentation.view.restaurants.сity.CityViewModel
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    fun bindProductFragmentViewModel(productViewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    fun bindLoginActivityViewModel(loginActivityViewModel: LoginActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmailLoginViewModel::class)
    fun bindEmailLoginViewModel(emailLoginViewModel: EmailLoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CodeLoginViewModel::class)
    fun bindCodeLoginViewModel(codeLoginViewModel: CodeLoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ColleaguesViewModel::class)
    fun bindColleaguesFragmentViewModel(colleaguesViewModel: ColleaguesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmployeeViewModel::class)
    fun bindEmployeeViewModel(employeeViewModel: EmployeeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadingActivityViewModel::class)
    fun bindLoadingActivityViewModel(loadingActivityViewModel: LoadingActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FaqViewModel::class)
    fun bindFaqViewModel(faqViewModel: FaqViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel::class)
    fun bindQuestionViewModel(questionViewModel: QuestionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @Reusable
    @ViewModelKey(RestaurantsViewModel::class)
    fun bindRestaurantsViewModel(restaurantsViewModel: RestaurantsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityViewModel::class)
    fun bindsCityViewModel(cityViewModel: CityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantBottomSheetViewModel::class)
    fun bindsRestaurantBottomSheetViewModel(restaurantBottomSheetViewModel: RestaurantBottomSheetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantDetailsViewModel::class)
    fun bindsRestaurantDetailsViewModel(restaurantDetailsViewModel: RestaurantDetailsViewModel): ViewModel
}
