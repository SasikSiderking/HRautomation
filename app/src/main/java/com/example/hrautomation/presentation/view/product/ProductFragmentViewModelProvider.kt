package com.example.hrautomation.presentation.view.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hrautomation.data.repository.ProductRepository
import com.example.hrautomation.domain.repository.IProductRepository
import javax.inject.Inject

class ProductFragmentViewModelProvider @Inject constructor(private val repo: IProductRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductFragmentViewModel(repo) as T
    }
}