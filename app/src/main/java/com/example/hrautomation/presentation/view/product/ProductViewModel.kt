package com.example.hrautomation.presentation.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.ProductRepository
import com.example.hrautomation.presentation.base.delegates.BaseListItem
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
    private val _exception = MutableLiveData<Throwable?>()

    val data: LiveData<List<BaseListItem>>
        get() = _data
    private val _data = MutableLiveData<List<BaseListItem>>(emptyList())

    val categories: LiveData<List<BaseListItem>>
        get() = _categories
    private val _categories = MutableLiveData<List<BaseListItem>>(emptyList())

    init {
        loadData()
    }

    fun setToastShownState() {
        _exception.postValue(null)
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            productRepo.getProductList(1, 100, "id")
                .onSuccess { productList ->
                    _data.postValue(productList.map { productToListedProductItemMapper.convert(it) })
                }.onFailure { exception: Throwable ->
                    Timber.e(exception)
                    _exception.postValue(exception)
                }

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
}