package com.example.hrautomation.presentation.view.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.BR
import com.example.hrautomation.presentation.model.ProductItem

class BindableRecyclerViewAdapter : RecyclerView.Adapter<BindableViewHolder>() {
    private var productItems: List<ProductItem> = emptyList()
    private val viewTypeToLayoutId: MutableMap<Int, Int> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewTypeToLayoutId[viewType] ?: 0,
            parent,
            false
        )
        return BindableViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = productItems[position]
        if (!viewTypeToLayoutId.containsKey(item.viewType)) {
            viewTypeToLayoutId[item.viewType] = item.layoutId
        }
        return item.viewType
    }

    override fun getItemCount(): Int = productItems.size

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(productItems[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<ProductItem>?) {
        productItems = items ?: emptyList()
        notifyDataSetChanged()
    }
}

class BindableViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(productItem: ProductItem) {
        binding.setVariable(BR.itemViewModel, productItem)
    }
}