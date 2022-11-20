package com.example.hrautomation.di

import com.example.hrautomation.data.model.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.data.model.FaqCategoryResponseToFaqCategoryMapper
import com.example.hrautomation.data.model.FaqQuestionResponseToFaqQuestionMapper
import com.example.hrautomation.data.model.ProductCategoryResponseToProductCategoryMapper
import com.example.hrautomation.data.model.ProductResponseToProductMapper
import com.example.hrautomation.presentation.model.EmployeeToColleagueItemMapper
import com.example.hrautomation.presentation.model.EmployeeToEmployeeItemMapper
import com.example.hrautomation.presentation.model.FaqCategoryToFaqCategoryItemMapper
import com.example.hrautomation.presentation.model.FaqQuestionToFaqQuestionItemMapper
import com.example.hrautomation.presentation.model.ProductCategoryToProductCategoryItemMapper
import com.example.hrautomation.presentation.model.ProductToListedProductItemMapper
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class MapperModule {
    @Reusable
    @Provides
    fun provideProductResponseToProductMapper(): ProductResponseToProductMapper = ProductResponseToProductMapper()

    @Reusable
    @Provides
    fun provideProductToListedProductItemMapper(): ProductToListedProductItemMapper = ProductToListedProductItemMapper()

    @Reusable
    @Provides
    fun provideEmployeesResponseToEmployeesMapper(): EmployeesResponseToEmployeesMapper = EmployeesResponseToEmployeesMapper()

    @Reusable
    @Provides
    fun provideEmployeeToColleagueItemMapper(): EmployeeToColleagueItemMapper = EmployeeToColleagueItemMapper()

    @Reusable
    @Provides
    fun provideEmployeeToEmployeeItemMapper(): EmployeeToEmployeeItemMapper = EmployeeToEmployeeItemMapper()

    @Reusable
    @Provides
    fun provideFaqCategoryResponseToFaqCategoryMapper(): FaqCategoryResponseToFaqCategoryMapper = FaqCategoryResponseToFaqCategoryMapper()

    @Reusable
    @Provides
    fun provideFaqCategoryToFaqCategoryItemMapper(): FaqCategoryToFaqCategoryItemMapper = FaqCategoryToFaqCategoryItemMapper()

    @Reusable
    @Provides
    fun provideFaqQuestionResponseToFaqQuestionMapper(): FaqQuestionResponseToFaqQuestionMapper = FaqQuestionResponseToFaqQuestionMapper()

    @Reusable
    @Provides
    fun provideFaqQuestionToFaqQuestionItemMapper(): FaqQuestionToFaqQuestionItemMapper = FaqQuestionToFaqQuestionItemMapper()

    @Reusable
    @Provides
    fun provideProductCategoryResponseToProductCategoryMapper(): ProductCategoryResponseToProductCategoryMapper =
        ProductCategoryResponseToProductCategoryMapper()

    @Reusable
    @Provides
    fun provideProductCategoryToProductCategoryItemMapper(): ProductCategoryToProductCategoryItemMapper = ProductCategoryToProductCategoryItemMapper()
}