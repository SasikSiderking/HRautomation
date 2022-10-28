package com.example.hrautomation.presentation.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.data.dispatcher.CoroutineDispatchers
import com.example.hrautomation.domain.repository.ProductRepository
import com.example.hrautomation.presentation.model.ProductItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductFragmentViewModel @Inject constructor(private val productRepo: ProductRepository, private val dispatchers: CoroutineDispatchers) : ViewModel() {

    val data: LiveData<List<ProductItem>>
        get() = _data
    private val _data = MutableLiveData<List<ProductItem>>(emptyList())

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            val productList = productRepo.getProductItemList()
            _data.postValue(productList)
        }
    }
}