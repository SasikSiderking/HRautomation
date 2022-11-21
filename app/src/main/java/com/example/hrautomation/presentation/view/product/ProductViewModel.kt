package com.example.hrautomation.presentation.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.domain.repository.ProductRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.model.ProductCategoryItem
import com.example.hrautomation.presentation.model.ProductCategoryToProductCategoryItemMapper
import com.example.hrautomation.presentation.model.ProductToListedProductItemMapper
import kotlinx.coroutines.launch
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
    private var _exception = MutableLiveData<Throwable?>()

    val message: LiveData<String?>
        get() = _message
    private var _message = MutableLiveData<String?>()

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>(emptyList())

    val categories: LiveData<List<ProductCategoryItem>>
        get() = _categories
    private val _categories = MutableLiveData<List<ProductCategoryItem>>(emptyList())

    init {
        loadData()
    }

    fun clearToastState() {
        _exception = MutableLiveData<Throwable?>()
        _message = MutableLiveData<String?>()
    }

    fun loadProductsByCategory(categoryId: Long?) {
        viewModelScope.launch(dispatchers.io) {
            categoryId?.let {
                loadProducts(productRepo.getProductsByCategory(it))
            } ?: run {
                loadProducts(productRepo.getProductList(1, 999, "id"))
            }
        }
    }

    fun orderProduct(id: Long) {
        viewModelScope.launch(dispatchers.io) {
            productRepo.orderProduct(id)
                .onSuccess {
                    _message.postValue("Продукт заказан")
                }
                .onFailure { exception: Throwable ->
                    Timber.e(exception)
                    _exception.postValue(exception)
                }
        }
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            loadProducts(productRepo.getProductList(1, 999, "id"))

            productRepo.getProductCategoryList()
                .onSuccess { categoryList ->
                    _categories.postValue(categoryList.map { productCategoryToProductCategoryItemMapper.convert(it) })
                }
                .onFailure { exception: Throwable ->
                    Timber.e(exception)
                    _exception.postValue(exception)
                }
        }
    }

    private fun loadProducts(result: Result<List<Product>>) {
        result
            .onSuccess { productList ->
                _data.postValue(productList.map { productToListedProductItemMapper.convert(it) })
            }.onFailure { exception: Throwable ->
                Timber.e(exception)
                _exception.postValue(exception)
            }
    }
}