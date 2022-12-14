package com.example.hrautomation.di

import com.example.hrautomation.data.model.TokenResponseToTokenMapper
import com.example.hrautomation.data.model.employee.EmployeesResponseToEmployeesMapper
import com.example.hrautomation.data.model.employee.ListEmployeeResponseToListEmployeeMapper
import com.example.hrautomation.data.model.faq.FaqCategoryResponseToFaqCategoryMapper
import com.example.hrautomation.data.model.faq.FaqQuestionResponseToFaqQuestionMapper
import com.example.hrautomation.data.model.products.ProductCategoryResponseToProductCategoryMapper
import com.example.hrautomation.data.model.products.ProductResponseToProductMapper
import com.example.hrautomation.presentation.model.colleagues.EmployeeToColleagueItemMapper
import com.example.hrautomation.presentation.model.colleagues.EmployeeToEmployeeItemMapper
import com.example.hrautomation.presentation.model.faq.FaqCategoryToFaqCategoryItemMapper
import com.example.hrautomation.presentation.model.faq.FaqQuestionToFaqQuestionItemMapper
import com.example.hrautomation.presentation.model.products.ProductCategoryToProductCategoryItemMapper
import com.example.hrautomation.presentation.model.products.ProductToListedProductItemMapper
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

    @Reusable
    @Provides
    fun provideTokenResponseToTokenMapper(): TokenResponseToTokenMapper = TokenResponseToTokenMapper()

    @Reusable
    @Provides
    fun provideListEmployeeResponseToListEmployeeMapper(): ListEmployeeResponseToListEmployeeMapper = ListEmployeeResponseToListEmployeeMapper()
}