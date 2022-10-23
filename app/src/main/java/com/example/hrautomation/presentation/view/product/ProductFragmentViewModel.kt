package com.example.hrautomation.presentation.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrautomation.domain.model.Product
import com.example.hrautomation.domain.repository.IProductRepository
import com.example.hrautomation.presentation.model.HeaderViewModel
import com.example.hrautomation.presentation.model.ProductViewModel
import com.example.hrautomation.presentation.model.ProductListingViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductFragmentViewModel @Inject constructor(private val repo: IProductRepository): ViewModel() {

    companion object{
        const val HEADER_ITEM = -1
        const val LISTING_ITEM = -2
    }

    val data: LiveData<List<ProductViewModel>>
        get() = _data
    private val _data = MutableLiveData<List<ProductViewModel>>(emptyList())

    init {
        loadData()
    }

    private fun loadData(){
        viewModelScope.launch {
            val productList = repo.getProductList()
            val productsBySection = productList.groupBy { it.section }
            val viewData = createViewData(productsBySection)
            _data.postValue(viewData)
        }
    }

    private fun createViewData(productsBySection: Map<String,List<Product>>): List<ProductViewModel>{
        val viewData = mutableListOf<ProductViewModel>()
        productsBySection.keys.forEach{
            viewData.add(HeaderViewModel(it))
            val products = productsBySection[it]
            products?.forEach{product: Product -> val item = ProductListingViewModel(product.section,product.img,product.name)
                viewData.add(item)
            }
        }
        return viewData
    }
}