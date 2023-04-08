package com.example.hrautomation.di

import com.example.hrautomation.data.repository.*
import com.example.hrautomation.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface RepositoryModule {

    @Reusable
    @Binds
    fun provideProductRepository(repo: ProductRepositoryImpl): ProductRepository

    @Reusable
    @Binds
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Reusable
    @Binds
    fun provideEmployeesRepository(employeesRepositoryImpl: EmployeesRepositoryImpl): EmployeesRepository

    @Reusable
    @Binds
    fun provideTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository

    @Reusable
    @Binds
    fun provideFaqRepository(faqRepositoryImpl: FaqRepositoryImpl): FaqRepository

    @Reusable
    @Binds
    fun provideMediaContentRepository(mediaContentRepositoryImpl: MediaContentRepositoryImpl): MediaContentRepository

    @Reusable
    @Binds
    fun provideRestaurantsRepository(restaurantsRepositoryImpl: RestaurantsRepositoryImpl): RestaurantsRepository

    @Reusable
    @Binds
    fun provideCachedCityLatLngRepository(cachedCityLatLngRepositoryImpl: CachedCityLatLngRepositoryImpl): CachedCityLatLngRepository

    @Reusable
    @Binds
    fun provideCachedBuildingsRepository(buildingsCacheManagerImpl: BuildingsCacheManagerImpl): BuildingsCacheManager

    @Reusable
    @Binds
    fun provideSocialRepository(socialRepositoryImpl: SocialRepositoryImpl): SocialRepository
}