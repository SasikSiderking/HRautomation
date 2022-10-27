package com.example.hrautomation.presentation.view.product

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.presentation.model.ProductItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager


class ProductAdapter(dataSet: List<ProductItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val delegatesManager: AdapterDelegatesManager<List<ProductItem>>
    private var dataSet: List<ProductItem>
    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(dataSet, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(dataSet, position, holder)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    init {
        this.dataSet = dataSet
        delegatesManager = AdapterDelegatesManager<List<ProductItem>>()

        delegatesManager.addDelegate(ProductAdapterDelegate())
    }

    fun update(data: List<ProductItem>) {
        dataSet = data
        notifyDataSetChanged()
    }
}