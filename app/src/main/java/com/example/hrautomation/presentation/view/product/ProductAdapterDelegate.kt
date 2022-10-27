package com.example.hrautomation.presentation.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hrautomation.databinding.ItemProductBinding
import com.example.hrautomation.presentation.model.ProductItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate


class ProductAdapterDelegate : AdapterDelegate<List<ProductItem>>() {
    public override fun isForViewType(items: List<ProductItem>, position: Int): Boolean {
        return true
    }

    public override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    public override fun onBindViewHolder(
        items: List<ProductItem>, position: Int,
        holder: RecyclerView.ViewHolder, @Nullable payloads: List<Any>
    ) {
        val vh = holder as ProductViewHolder
        val product: ProductItem = items[position]
        vh.name.text = product.name
        Glide.with(vh.img.context).load(product.img).placeholder(com.example.hrautomation.R.drawable.ic_launcher_foreground).into(vh.img)
    }

    internal class ProductViewHolder(binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView
        val img: ImageView

        init {
            name = binding.productName
            img = binding.productImage
        }
    }
}