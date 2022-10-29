package com.example.hrautomation.presentation.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hrautomation.databinding.ItemProductBinding
import com.example.hrautomation.presentation.model.ListedProductItem
import com.example.hrautomation.presentation.model.ProductItem
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ProductListItemAdapterDelegate :
    AbsListItemAdapterDelegate<ListedProductItem, ProductItem, ProductListItemAdapterDelegate.ProductViewHolder>() {

    override fun isForViewType(
        item: ProductItem,
        items: MutableList<ProductItem>,
        position: Int
    ): Boolean {
        return item is ListedProductItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        product: ListedProductItem,
        vh: ProductViewHolder,
        payloads: MutableList<Any>
    ) {
        vh.name.text = product.name
        Glide.with(vh.img.context).load(product.img)
            .placeholder(com.example.hrautomation.R.drawable.ic_launcher_foreground).into(vh.img)
    }

    class ProductViewHolder(binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView
        val img: ImageView

        init {
            name = binding.productName
            img = binding.productImage
        }
    }
}