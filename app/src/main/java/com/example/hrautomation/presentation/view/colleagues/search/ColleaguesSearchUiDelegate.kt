package com.example.hrautomation.presentation.view.colleagues.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hrautomation.R
import com.example.hrautomation.databinding.ItemEmployeeBinding
import com.example.hrautomation.presentation.base.search.BaseSearchViewHolder
import com.example.hrautomation.presentation.base.search.SearchUiDelegate
import com.example.hrautomation.presentation.model.colleagues.ListedColleagueItem

class ColleaguesSearchUiDelegate :
    SearchUiDelegate<ListedColleagueItem, ItemEmployeeBinding, BaseSearchViewHolder<ItemEmployeeBinding>> {

    private var onResultClickListener: ((Long) -> Unit)? = null

    override fun getLayout(parent: ViewGroup, inflater: LayoutInflater): ItemEmployeeBinding {
        return ItemEmployeeBinding.inflate(inflater, parent, false)
    }

    override fun bind(item: ListedColleagueItem, holder: BaseSearchViewHolder<ItemEmployeeBinding>) {
        with(holder.viewBinding) {
            employeeName.text = item.name
            employeePost.text = item.post

            Glide.with(employeeImage)
                .load(item.pictureUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_confused)
                .into(employeeImage)

            root.setOnClickListener {
                onResultClickListener?.invoke(item.id)
            }
        }
    }

    fun setOnResultClickListener(listener: ((Long) -> Unit)?) {
        this.onResultClickListener = listener
    }
}