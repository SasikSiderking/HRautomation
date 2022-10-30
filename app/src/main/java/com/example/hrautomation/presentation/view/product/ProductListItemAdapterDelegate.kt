package com.example.hrautomation.presentation.view.product

import android.util.Log
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
import com.example.hrautomation.presentation.model.ListedProductItem
import com.example.hrautomation.presentation.view.product.ProductListItemAdapterDelegate.ProductViewHolder

class ProductListItemAdapterDelegate
    : BaseItemAdapterDelegate<ListedProductItem, ProductViewHolder>(), OnViewHolderClickListener<ProductViewHolder> {

    override fun isForViewType(item: BaseListItem): Boolean {
        return item is ListedProductItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, this)
    }

    override fun onBind(
        product: ListedProductItem,
        vh: ProductViewHolder,
        payloads: List<Any>
    ) {
        vh.name.text = product.name

        Glide.with(vh.img)
            .load(product.img)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(vh.img)
    }

    override fun onViewHolderClick(view: View, holder: ProductViewHolder) {
        val item = getItemForViewHolder(holder)
        Log.d("ProductListItemAdapterDelegate", "Clicked on view holder ${item.id}")
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