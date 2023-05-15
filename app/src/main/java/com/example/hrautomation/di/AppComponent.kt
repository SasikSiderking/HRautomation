package com.example.hrautomation.di

import com.example.hrautomation.di.network.ApiModule
import com.example.hrautomation.di.utils.ItemFactoryModule
import com.example.hrautomation.di.utils.MapperModule
import com.example.hrautomation.di.utils.ResourcesModule
import com.example.hrautomation.di.view_model.PublisherModule
import com.example.hrautomation.di.view_model.ViewModelFactoryModule
import com.example.hrautomation.di.view_model.ViewModelModule
import com.example.hrautomation.presentation.view.activity.MainActivity
import com.example.hrautomation.presentation.view.colleagues.ColleaguesFragment
import com.example.hrautomation.presentation.view.colleagues.employee.EmployeeActivity
import com.example.hrautomation.presentation.view.faq.FaqFragment
import com.example.hrautomation.presentation.view.faq.question.QuestionActivity
import com.example.hrautomation.presentation.view.loading.LoadingActivity
import com.example.hrautomation.presentation.view.loading.code.CodeLoginFragment
import com.example.hrautomation.presentation.view.loading.email.EmailLoginFragment
import com.example.hrautomation.presentation.view.loading.login.LoginActivity
import com.example.hrautomation.presentation.view.product.ProductFragment
import com.example.hrautomation.presentation.view.profile.ProfileActivity
import com.example.hrautomation.presentation.view.restaurants.RestaurantsFragment
import com.example.hrautomation.presentation.view.restaurants.list.RestaurantsListFragment
import com.example.hrautomation.presentation.view.restaurants.map.RestaurantsMapFragment
import com.example.hrautomation.presentation.view.restaurants.restaurant.RestaurantBottomSheet
import com.example.hrautomation.presentation.view.restaurants.restaurant_details.RestaurantDetailsActivity
import com.example.hrautomation.presentation.view.restaurants.restaurant_details.RestaurantReviewActivity
import com.example.hrautomation.presentation.view.restaurants.сity.CityBottomSheet
import com.example.hrautomation.presentation.view.social.SocialFragment
import com.example.hrautomation.presentation.view.social.details.EventDetailsActivity
import com.example.hrautomation.presentation.view.social.filter.EventFilterActivity
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
        DispatchersModule::class,
        ContentResolverModule::class,
        PublisherModule::class,
        ResourcesModule::class,
        ItemFactoryModule::class
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

    fun inject(fragment: RestaurantsMapFragment)
    fun inject(sheet: CityBottomSheet)
    fun inject(fragment: RestaurantsFragment)
    fun inject(fragment: RestaurantsListFragment)
    fun inject(sheet: RestaurantBottomSheet)
    fun inject(activity: RestaurantDetailsActivity)
    fun inject(activity: RestaurantReviewActivity)

    fun inject(fragment: SocialFragment)
    fun inject(activity: EventFilterActivity)
    fun inject(activity: EventDetailsActivity)
}
