package com.example.hrautomation.di

import com.example.hrautomation.data.repository.EmployeesRepositoryImpl
import com.example.hrautomation.data.repository.FaqRepositoryImpl
import com.example.hrautomation.data.repository.ProductRepositoryImpl
import com.example.hrautomation.data.repository.TokenRepositoryImpl
import com.example.hrautomation.data.repository.UserRepositoryImpl
import com.example.hrautomation.domain.repository.EmployeesRepository
import com.example.hrautomation.domain.repository.FaqRepository
import com.example.hrautomation.domain.repository.ProductRepository
import com.example.hrautomation.domain.repository.TokenRepository
import com.example.hrautomation.domain.repository.UserRepository
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
}