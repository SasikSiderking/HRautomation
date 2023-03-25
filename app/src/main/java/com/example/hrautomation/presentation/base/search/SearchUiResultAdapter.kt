package com.example.hrautomation.presentation.base.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.hrautomation.presentation.base.delegates.BaseListItem

class SearchUiResultAdapter<Item : BaseListItem, VB : ViewBinding>(
    private val uiDelegate: SearchUiDelegate<Item, VB, BaseSearchViewHolder<VB>>
) : RecyclerView.Adapter<BaseSearchViewHolder<VB>>() {

    private var items: List<Item> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseSearchViewHolder<VB> {
        val viewBinding = uiDelegate.getLayout(parent, LayoutInflater.from(parent.context))
        return BaseSearchViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: BaseSearchViewHolder<VB>, position: Int) {
        val item = items[position]
        uiDelegate.bind(item, holder)
    }

    override fun getItemCount(): Int = items.size

}

class BaseSearchViewHolder<VB : ViewBinding>(val viewBinding: VB) : RecyclerView.ViewHolder(viewBinding.root)