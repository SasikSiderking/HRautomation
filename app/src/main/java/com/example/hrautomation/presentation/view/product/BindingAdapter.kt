package com.example.hrautomation.presentation.view.product

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hrautomation.R
import com.example.hrautomation.presentation.model.ProductViewModel


@BindingAdapter("itemViewModels")
fun bindItemViewModels(recyclerView: RecyclerView, productViewModels: List<ProductViewModel>?) {
    val adapter = getOrCreateAdapter(recyclerView)
    adapter.updateItems(productViewModels)
}

@BindingAdapter("img")
fun loadImage(view: ImageView, img: String) {
    Glide.with(view.context).load(img).placeholder(R.drawable.ic_launcher_foreground).into(view)
}

private fun getOrCreateAdapter(recyclerView: RecyclerView): BindableRecyclerViewAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is BindableRecyclerViewAdapter) {
        recyclerView.adapter as BindableRecyclerViewAdapter
    } else {
        val bindableRecyclerAdapter = BindableRecyclerViewAdapter()
        recyclerView.adapter = bindableRecyclerAdapter
        bindableRecyclerAdapter
    }
}