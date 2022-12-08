package com.example.hrautomation.presentation.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.ProductSortBy
import com.example.hrautomation.domain.repository.ProductRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.ProductCategoryItem
import com.example.hrautomation.presentation.model.ProductCategoryToProductCategoryItemMapper
import com.example.hrautomation.presentation.model.ProductToListedProductItemMapper
import com.example.hrautomation.utils.tryLaunch
import kotlinx.coroutines.Job
import timber.log.Timber
import javax.inject.Inject


class ProductViewModel @Inject constructor(
    private val productRepo: ProductRepository,
    private val dispatchers: CoroutineDispatchers,
    private val productToListedProductItemMapper: ProductToListedProductItemMapper,
    private val productCategoryToProductCategoryItemMapper: ProductCategoryToProductCategoryItemMapper
) : ViewModel() {

    val exception: LiveData<Throwable?>
        get() = _exception
    private val _exception = MutableLiveData<Throwable?>()

    val message: LiveData<String?>
        get() = _message
    private var _message = MutableLiveData<String?>()

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>()

    val categories: LiveData<List<ProductCategoryItem>>
        get() = _categories
    private val _categories = MutableLiveData<List<ProductCategoryItem>>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>(true)

    init {
        loadData()
    }

    fun clearExceptionState() {
        _exception.postValue(null)
    }

    fun clearMessageState() {
        _message.postValue(null)
    }

    fun loadProductsByCategory(categoryId: Long?) {
        categoryId?.let {
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
        }
            ?: loadProductsJooba()
    }

    fun orderProduct(id: Long) {
        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                productRepo.orderProduct(id)
                _message.postValue("Продукт заказан")
            },
            doOnError = { error ->
                Timber.e(error)
                _exception.postValue(error)
            }
        )
    }

    private fun loadData() {

        loadProductsJooba()

        viewModelScope.tryLaunch(
            contextPiece = dispatchers.io,
            doOnLaunch = {
                _categories.postValue(
                    productRepo.getProductCategoryList()
                        .map { productCategoryToProductCategoryItemMapper.convert(it) })
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