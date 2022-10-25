package com.example.hrautomation.di

import com.example.hrautomation.data.repository.EmployeesRepository
import com.example.hrautomation.data.repository.ProductRepository
import com.example.hrautomation.data.repository.TokenRepository
import com.example.hrautomation.data.repository.UserRepository
import com.example.hrautomation.domain.repository.IEmployeesRepository
import com.example.hrautomation.domain.repository.IProductRepository
import com.example.hrautomation.domain.repository.ITokenRepository
import com.example.hrautomation.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface RepositoryModule {

    @Reusable
    @Binds
    fun provideProductRepository(repo: ProductRepository): IProductRepository

    @Reusable
    @Binds
    fun provideUserRepository(userRepository: UserRepository): IUserRepository

    @Reusable
    @Binds
    fun provideEmployeesRepository(employeesRepository: EmployeesRepository): IEmployeesRepository

    @Reusable
    @Binds
    fun provideTokenRepository(tokenRepository: TokenRepository): ITokenRepository
}