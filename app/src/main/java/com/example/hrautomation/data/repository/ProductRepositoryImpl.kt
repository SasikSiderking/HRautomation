package com.example.hrautomation.data.repository

import com.example.hrautomation.data.api.ProductApi
import com.example.hrautomation.data.model.ProductCategoryResponse
import com.example.hrautomation.data.model.ProductCategoryResponseToProductCategoryMapper
import com.example.hrautomation.data.model.ProductResponse
import com.example.hrautomation.data.model.ProductResponseToProductMapper
import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.domain.model.ProductCategory
import com.example.hrautomation.domain.model.ProductSortBy
import com.example.hrautomation.domain.repository.ProductRepository
import com.example.hrautomation.utils.asDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productResponseToProductMapper: ProductResponseToProductMapper,
    private val productCategoryResponseToProductCategoryMapper: ProductCategoryResponseToProductCategoryMapper
) : ProductRepository {

    override suspend fun getProductList(pageNumber: Int, size: Int, sortBy: ProductSortBy): Result<List<Product>> {
        return productApi.getProductResponseList(pageNumber, size, sortBy.sortBy).asDomain { productList: List<ProductResponse> ->
            productList.map { productResponseToProductMapper.convert(it) }
        }
    }

    override suspend fun orderProduct(id: Long): Result<Unit> {
        return productApi.orderProduct(id).asDomain { }
    }

    override suspend fun getProductCategoryList(): Result<List<ProductCategory>> {
        return productApi.getProductCategoriesResponse().asDomain { productCategoryList: List<ProductCategoryResponse> ->
            productCategoryList.map { productCategoryResponseToProductCategoryMapper.convert(it) }
        }
    }

    override suspend fun getProductsByCategory(categoryId: Long): Result<List<Product>> {
        return productApi.getProductsByCategory(categoryId).asDomain { productList: List<ProductResponse> ->
            productList.map { productResponseToProductMapper.convert(it) }
        }
    }
}