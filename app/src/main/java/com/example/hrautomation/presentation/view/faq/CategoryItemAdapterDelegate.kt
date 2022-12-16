package com.example.hrautomation.presentation.view.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hrautomation.databinding.ItemFaqRecyclerviewBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.faq.FaqCategoryItem
import com.example.hrautomation.presentation.view.faq.CategoryItemAdapterDelegate.FaqCategoryItemViewHolder

class CategoryItemAdapterDelegate(private val onFaqCategoryClickListener: OnFaqCategoryClickListener) :
    BaseItemAdapterDelegate<FaqCategoryItem, FaqCategoryItemViewHolder>(),
    OnViewHolderClickListener<FaqCategoryItemViewHolder> {
    override fun isForViewType(item: BaseListItem): Boolean {
        return item is FaqCategoryItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): FaqCategoryItemViewHolder {
        val binding = ItemFaqRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FaqCategoryItemViewHolder(binding, this)
    }

    override fun onBind(item: FaqCategoryItem, holder: FaqCategoryItemViewHolder, payloads: List<Any>) {
        holder.name.text = item.name
    }

    override fun onViewHolderClick(view: View, holder: FaqCategoryItemViewHolder) {
        val item = getItemForViewHolder(holder)
        onFaqCategoryClickListener.onClick(item.id, item.name)
    }

    class FaqCategoryItemViewHolder(binding: ItemFaqRecyclerviewBinding, clickListener: OnViewHolderClickListener<FaqCategoryItemViewHolder>) :
        ClickableViewHolder<FaqCategoryItemViewHolder>(binding.root, clickListener) {
        val name: TextView

        init {
            name = binding.category
        }
    }
}