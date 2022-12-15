package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.ProductApi
import com.example.hrautomation.data.model.products.ProductCategoryResponseToProductCategoryMapper
import com.example.hrautomation.data.model.products.ProductResponseToProductMapper
import com.example.hrautomation.domain.model.products.Product
import com.example.hrautomation.domain.model.products.ProductCategory
import com.example.hrautomation.domain.model.products.ProductSortBy
import com.example.hrautomation.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productResponseToProductMapper: ProductResponseToProductMapper,
    private val productCategoryResponseToProductCategoryMapper: ProductCategoryResponseToProductCategoryMapper
) : ProductRepository {

    override suspend fun getProductList(pageNumber: Int, size: Int, sortBy: ProductSortBy): List<Product> {
        return productApi.getProductResponseList(pageNumber, size, sortBy.sortBy).map { productResponseToProductMapper.convert(it) }
    }

    override suspend fun orderProduct(id: Long) {
        return productApi.orderProduct(id)
    }

    override suspend fun getProductCategoryList(): List<ProductCategory> {
        return productApi.getProductCategoriesResponse().map { productCategoryResponseToProductCategoryMapper.convert(it) }
    }

    override suspend fun getProductsByCategory(categoryId: Long): List<Product> {
        return productApi.getProductsByCategory(categoryId).map { productResponseToProductMapper.convert(it) }
    }
}