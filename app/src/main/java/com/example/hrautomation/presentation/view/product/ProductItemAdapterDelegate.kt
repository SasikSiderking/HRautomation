package com.example.hrautomation.presentation.view.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.hrautomation.R
import com.example.hrautomation.databinding.ItemProductBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.products.ProductItem
import com.example.hrautomation.presentation.view.product.ProductItemAdapterDelegate.ProductViewHolder

class ProductItemAdapterDelegate(private val onProductClickListener: OnProductClickListener) : BaseItemAdapterDelegate<ProductItem, ProductViewHolder>(),
    OnViewHolderClickListener<ProductViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is ProductItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, this)
    }

    override fun onBind(
        item: ProductItem,
        holder: ProductViewHolder,
        payloads: List<Any>
    ) {
        holder.name.text = item.name

        Glide.with(holder.img)
            .load(item.pictureUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.img)
    }

    override fun onViewHolderClick(view: View, holder: ProductViewHolder) {
        val item = getItemForViewHolder(holder)
        onProductClickListener.onClick(item.id, item.name)
    }

    class ProductViewHolder(
        binding: ItemProductBinding,
        clickListener: OnViewHolderClickListener<ProductViewHolder>
    ) : ClickableViewHolder<ProductViewHolder>(binding.root, clickListener) {
        val name: TextView
        val img: ImageView

        init {
            name = binding.productName
            img = binding.productImage
        }
    }
}