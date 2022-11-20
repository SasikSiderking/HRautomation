package com.example.hrautomation.presentation.view.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hrautomation.databinding.ItemProductCategoryBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.ProductCategoryItem
import com.example.hrautomation.presentation.view.product.ProductCategoryItemAdapterDelegate.ProductCategoryViewHolder
import timber.log.Timber

class ProductCategoryItemAdapterDelegate : BaseItemAdapterDelegate<ProductCategoryItem, ProductCategoryViewHolder>(),
    OnViewHolderClickListener<ProductCategoryViewHolder> {
    override fun isForViewType(item: BaseListItem): Boolean {
        return item is ProductCategoryItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): ProductCategoryViewHolder {
        val binding = ItemProductCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductCategoryViewHolder(binding, this)
    }

    override fun onBind(
        item: ProductCategoryItem,
        holder: ProductCategoryViewHolder,
        payloads: List<Any>
    ) {
        holder.name.text = item.name
    }

    override fun onViewHolderClick(view: View, holder: ProductCategoryViewHolder) {
        val item = getItemForViewHolder(holder)
        Timber.tag("ProductItemAdapterDelegate").d("Clicked on view holder %s", item.id)
    }

    class ProductCategoryViewHolder(
        binding: ItemProductCategoryBinding,
        clickListener: OnViewHolderClickListener<ProductCategoryViewHolder>
    ) : ClickableViewHolder<ProductCategoryViewHolder>(binding.root, clickListener) {
        val name: TextView

        init {
            name = binding.category
        }
    }
}