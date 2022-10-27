package com.example.hrautomation.presentation.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.repository.IProductRepository
import com.example.hrautomation.presentation.model.ProductItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductFragmentViewModel @Inject constructor(private val repo: IProductRepository) : ViewModel() {

    val data: LiveData<List<ProductItem>>
        get() = _data
    private val _data = MutableLiveData<List<ProductItem>>(emptyList())

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val productList = repo.getProductItemList()
            _data.postValue(productList)
        }
    }
}