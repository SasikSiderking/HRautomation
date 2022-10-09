package com.example.hrautomation.presentation.model

import androidx.annotation.LayoutRes
import com.example.hrautomation.R
import com.example.hrautomation.presentation.view.product.ProductFragmentViewModel

interface ItemViewModel {
    @get:LayoutRes
    val layoutId: Int
    val viewType: Int
    get()=0
}

class HeaderViewModel(val title: String) : ItemViewModel {
    override val layoutId: Int = R.layout.item_header
    override val viewType: Int = ProductFragmentViewModel.HEADER_ITEM
}
class ProductListingViewModel(val section: String, val img: String, val name: String) :
    ItemViewModel {
    override val layoutId: Int = R.layout.item_product_listing
    override val viewType: Int = ProductFragmentViewModel.LISTING_ITEM
}