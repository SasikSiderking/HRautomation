package com.example.hrautomation.presentation.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.R
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.products.ProductSortBy
import com.example.hrautomation.domain.repository.ProductRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.viewModel.BaseViewModel
import com.example.hrautomation.presentation.model.products.ProductCategoryItem
import com.example.hrautomation.presentation.model.products.ProductCategoryToProductCategoryItemMapper
import com.example.hrautomation.presentation.model.products.ProductToListedProductItemMapper
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject


class ProductViewModel @Inject constructor(
    private val productRepo: ProductRepository,
    private val dispatchers: CoroutineDispatchers,
    private val productToListedProductItemMapper: ProductToListedProductItemMapper,
    private val productCategoryToProductCategoryItemMapper: ProductCategoryToProductCategoryItemMapper
) : BaseViewModel() {

    val message: LiveData<Int?>
        get() = _message
    private var _message = MutableLiveData<Int?>()

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>()

    val categories: LiveData<List<ProductCategoryItem>>
        get() = _categories
    private val _categories = MutableLiveData<List<ProductCategoryItem>>()

    init {
        loadData()
    }

    fun reload() {
        viewModelScope.coroutineContext.cancelChildren()
        clearExceptionState()
        loadData()
    }

    fun clearMessageState() {
        _message.postValue(null)
    }

    fun loadProductsByCategory(categoryId: Long?) {
        if (categoryId != null) {
            viewModelScope.tryLaunch(
                contextPiece = dispatchers.io,
                doOnLaunch = {
                    _data.postValue(
                        productRepo.getProductsByCategory(categoryId)
                            .map { productToListedProductItemMapper.convert(it) })
                },
                doOnError = { error ->
                    Timber.e(error)
                    _exception.postValue(error)
                }
            )
        } else {
            loadProductsJooba()
        }
    }

    fun orderProduct(id: Long) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                withContext(NonCancellable) {
                    productRepo.orderProduct(id)
                    _message.postValue(R.string.order_product_ordered)
                }
            },
            doOnError = { error ->
                Timber.e(error)
                _message.postValue(R.string.toast_overall_error)
            }
        )
    }

    private fun loadData() {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                val productsDeferred = async(dispatchers.io) {
                    productRepo.getProductList(
                        PAGE_NUMBER,
                        PAGE_SIZE,
                        ProductSortBy.ID
                    ).map { productToListedProductItemMapper.convert(it) }
                }
                val categoriesDeferred = async(dispatchers.io) {
                    productRepo.getProductCategoryList()
                        .map { productCategoryToProductCategoryItemMapper.convert(it) }
                }

                val products = productsDeferred.await()
                val categories = categoriesDeferred.await()

                _data.postValue(products)
                _categories.postValue(categories)
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    private fun loadProductsJooba(): Job {
        return viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                _data.postValue(productRepo.getProductList(
                    PAGE_NUMBER,
                    PAGE_SIZE,
                    ProductSortBy.ID
                ).map { productToListedProductItemMapper.convert(it) })
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    private companion object {
        const val PAGE_SIZE = 100
        const val PAGE_NUMBER = 0
    }
}